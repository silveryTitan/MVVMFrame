package com.silverytitan.mvvmframe.navigationBar;

import com.blankj.utilcode.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by silveryTitan on 2021/4/30 0030.
 * Desc:
 */
public final class NavigationBarConfig /*implements Parcelable */ {
    public int navigationHeight;
    public int iconWidth;
    public int iconSize;
    public List<Integer> icons;
    public int imgWidth;
    public int imgHeight;
    public int imgSize;

    public NavigationBarConfig() {
    }

    public static NavigationBarConfig getInstance() {
        return NavigationBarConfig.InstanceHolder.INSTANCE;
    }

    public static NavigationBarConfig getCleanInstance() {
        NavigationBarConfig selectionSpec = getInstance();
        selectionSpec.initDefaultValue();
        return selectionSpec;
    }

    protected void initDefaultValue() {
        navigationHeight = 14;
        iconSize = 4;
        iconWidth = ScreenUtils.getAppScreenWidth() / iconSize;
        icons = new ArrayList<>();
        imgWidth = 0;
        imgHeight = 0;
        imgSize = 0;
    }

    private static final class InstanceHolder {
        private static final NavigationBarConfig INSTANCE = new NavigationBarConfig();
    }
}