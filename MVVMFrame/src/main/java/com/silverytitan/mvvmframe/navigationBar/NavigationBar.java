package com.silverytitan.mvvmframe.navigationBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.silverytitan.mvvmframe.R;
import com.silverytitan.mvvmframe.interfaces.OnTouchClickListener;
import com.silverytitan.mvvmframe.interfaces.OnTouchLongListener;
import com.silverytitan.mvvmframe.interfaces.OnTouchMoveListener;

import java.text.DecimalFormat;
import java.util.List;

public class NavigationBar {

    private NavigationBarConfig navigationBarConfig;
    private Activity activity;
    private OnTouchClickListener onTouchClickListener;
    private OnTouchMoveListener onTouchMoveListener;
    private OnTouchLongListener onTouchLongListener;
    private float x;
    private float y;
    private float startX;
    private float startY;
    //按下和移动时的时间，用于判断是否是长按事件
    private long timeDown, timeMove;

    @NonNull
    public NavigationBar get(@NonNull Activity activity) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalArgumentException("You need to run this framework on the main thread.");
        }
        this.activity = activity;
        navigationBarConfig = NavigationBarConfig.getCleanInstance();
        return this;
    }

    /**
     * 底部导航栏占应用所占屏幕的百分比(除状态栏和虚拟导航栏(返回、home、多任务))，输入值为0-1
     *
     * @param percentageHeight 屏幕百分比高度
     */
    public NavigationBar setNavigationHeight(float percentageHeight) {
        if (percentageHeight > 1 || percentageHeight < 0) {
            throw new VerifyError("percentageHeight Value cannot be greater than 1 or less than 0");
        }
        //percentageHeight值为0时，默认恢复为初始值
        if (percentageHeight == 0) return this;
        //小数不足两位补全
        String formatHeight = new DecimalFormat("0.00").format(percentageHeight);
        int replaceHeight = Integer.parseInt(formatHeight.replace("0.", ""));
        Log.e("silvery Titan", "setNavigationHeight: replaceHeight " + replaceHeight);
        navigationBarConfig.navigationHeight = replaceHeight;
        return this;
    }

    /**
     * @param pxHeight 指定单位为px的导航栏高度
     */
    public void setNavigationHeight(int pxHeight) {
        navigationBarConfig.navigationHeight = ScreenUtils.getAppScreenHeight() / pxHeight;
    }

    /**
     * @iconWidth 参数为应用所占屏幕的宽度除以图标集合的大小 eg:1080(屏幕宽)/4(图标集合大小)
     * 默认图标宽度为 屏幕宽度/4
     * @iconSize 默认图标大小为4
     * @icons 图标或样式集合
     */
    public NavigationBar setPage(@IdRes List<Integer> icons) {
        navigationBarConfig.iconSize = icons.size();
        navigationBarConfig.iconWidth = ScreenUtils.getAppScreenWidth() / icons.size();
        navigationBarConfig.icons = icons;
        return this;
    }

    /**
     * 设置图标宽高
     *
     * @param pxWidth  图标宽度 px单位
     * @param pxHeight 图标高度 px单位
     */
    public NavigationBar setImgSize(int pxWidth, int pxHeight) {
        navigationBarConfig.imgWidth = pxWidth;
        navigationBarConfig.imgHeight = pxHeight;
        return this;
    }

    /**
     * 设置图标宽高
     *
     * @param pxSize 图标宽高度 px单位
     */
    public NavigationBar setImgSize(int pxSize) {
        navigationBarConfig.imgSize = pxSize;
        return this;
    }

    public NavigationBar setOnTouchClickListener(OnTouchClickListener onTouchClickListener) {
        this.onTouchClickListener = onTouchClickListener;
        return this;
    }

    public NavigationBar setOnTouchMoveListener(OnTouchMoveListener onTouchMoveListener) {
        this.onTouchMoveListener = onTouchMoveListener;
        return this;
    }

    public NavigationBar setOnTouchLongListener(OnTouchLongListener onTouchLongListener) {
        this.onTouchLongListener = onTouchLongListener;
        return this;
    }

    public void show() {
        View view = LayoutInflater.from(activity).inflate(R.layout.bottom_bavigation_bar, null, false);
        LinearLayout llNavigationBar = view.findViewById(R.id.llNavigationBar);
        llNavigationBar.setBackgroundColor(ColorUtils.getColor(R.color.picture_color_9b));
        ViewGroup.LayoutParams params = llNavigationBar.getLayoutParams();
        //应用内的高度除以底部栏百分比高度等于实际展示所占px
        params.height = ScreenUtils.getAppScreenHeight() / navigationBarConfig.navigationHeight;
        llNavigationBar.setLayoutParams(params);
        addNavigationView(llNavigationBar);
        activity.getWindow().setContentView(view);
    }

    /**
     * 添加底部导航子布局
     *
     * @param llNavigationBar 所在父布局
     */
    private void addNavigationView(LinearLayout llNavigationBar) {
        for (int i = 0; i < navigationBarConfig.icons.size(); i++) {
            Integer child = navigationBarConfig.icons.get(i);
            llNavigationBar.addView(addChildView(child, i));
        }
    }

    /**
     * 添加底部导航子布局
     *
     * @param childId 子布局id
     * @param i       点击位置
     */
    private LinearLayout addChildView(Integer childId, int i) {
        LinearLayout llChildRoot = new LinearLayout(activity);
        llChildRoot.setTag(i);
        ImageView imageView = new ImageView(activity);
        imageView.setBackground(ContextCompat.getDrawable(activity, childId));
        if (navigationBarConfig.imgHeight != 0 && navigationBarConfig.imgWidth != 0) {
            imageView.setLayoutParams(new LinearLayout.LayoutParams(navigationBarConfig.imgWidth, navigationBarConfig.imgHeight));
        } else if (navigationBarConfig.imgSize != 0) {
            imageView.setLayoutParams(new LinearLayout.LayoutParams(navigationBarConfig.imgSize, navigationBarConfig.imgSize));
        } else {
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        llChildRoot.addView(imageView);
        llChildRoot.setLayoutParams(new LinearLayout.LayoutParams(navigationBarConfig.iconWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        llChildRoot.setGravity(Gravity.CENTER);
        setClick(llChildRoot);
        return llChildRoot;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setClick(LinearLayout llChildRoot) {
        llChildRoot.setOnTouchListener((v, event) -> {
            // 获取相对屏幕的坐标，即以屏幕左上角为原点
            x = event.getRawX();
            y = event.getRawY() - 25; // 25是系统状态栏的高度
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = x;
                    startY = y;
                    timeDown = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_UP:
                    timeMove = System.currentTimeMillis();
                    long durationMs = timeMove - timeDown;
                    int position = (int) v.getTag();
                    if (durationMs > 800) {
                        if (Math.abs(x - startX) >= 1.5 && Math.abs(y - startY) >= 1.5)
                            onTouchMoveListener.onMoveClick(position);
                        else onTouchLongListener.onLongClick(position);
                    } else {
                        if (Math.abs(x - startX) < 1.5 && Math.abs(y - startY) < 1.5)
                            onTouchClickListener.onClick(position);
                        else onTouchMoveListener.onMoveClick(position);
                    }
                    break;
            }
            return true;
        });
    }
}