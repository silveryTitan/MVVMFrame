package com.silverytitan.mvvmframe.expand

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.View
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.StringUtils

/**
 *资源操作扩展类相关
 */
fun Context.color(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.string(@StringRes id: Int) = resources.getString(id)

fun Context.stringArray(@ArrayRes id: Int): Array<String> = resources.getStringArray(id)

fun Context.drawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

fun Context.dimenPx(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

fun View.color(@ColorRes id: Int) = context.color(id)

fun View.string(@StringRes id: Int) = context.string(id)

fun View.stringArray(@ArrayRes id: Int): Array<String> = context.stringArray(id)

fun View.drawable(@DrawableRes id: Int) = context.drawable(id)

fun View.dimenPx(@DimenRes id: Int) = context.dimenPx(id)

fun Fragment.color(@ColorRes id: Int) = context!!.color(id)

fun Fragment.string(@StringRes id: Int) = context!!.string(id)

fun Fragment.stringArray(@ArrayRes id: Int) = context!!.stringArray(id)

fun Fragment.drawable(@DrawableRes id: Int) = context!!.drawable(id)

fun Fragment.dimenPx(@DimenRes id: Int) = context!!.dimenPx(id)

fun Int.dp2Px() = ConvertUtils.dp2px(this.toFloat())

fun Int.px2Dp() = ConvertUtils.px2dp(this.toFloat())

fun Int.sp2px() = ConvertUtils.sp2px(this.toFloat())

fun Int.px2sp() = ConvertUtils.px2sp(this.toFloat())

fun Int.color() = ColorUtils.getColor(this)

fun Int.string() = StringUtils.getString(this)

fun Any.toJson() = GsonUtils.toJson(this)!!

fun Any.loge(distinguishTag: String) {
    Log.e("silvery Titan", "$distinguishTag：$this")
}

fun Any.loge() {
    Log.e("silvery Titan", this.toString())
}

/**
 * 设置带文字布局控件宽度
 */
fun String.setTextWidth2View(view: View) {
    val layoutParams = view.layoutParams
    layoutParams.width = Paint().measureText(this).toInt() * length + 14
    view.layoutParams = layoutParams
}

/**
 * 设置带文字布局控件宽度
 */
fun String.getTextWidth() = Paint().measureText(this).toInt()

