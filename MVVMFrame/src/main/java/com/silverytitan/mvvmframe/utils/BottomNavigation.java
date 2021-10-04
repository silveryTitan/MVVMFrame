package com.silverytitan.mvvmframe.utils;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.silverytitan.mvvmframe.navigationBar.NavigationBar;

import org.jetbrains.annotations.NotNull;

public class BottomNavigation {
    private volatile static BottomNavigation getInstance;
    private static int loadNumberOfTimes = 0;

    private BottomNavigation() {
    }

    @NotNull
    public static NavigationBar with(@NotNull Activity activity) {
        if (loadNumberOfTimes > 0) {
            throw new IllegalArgumentException("This frame can only be initialized once on the main page.");
        }
        loadNumberOfTimes++;
        return getNavigationBar(activity).get(activity);
    }

    @NonNull
    private static NavigationBar getNavigationBar(@Nullable Context context) {
        if (context == null) {
            throw new NullPointerException("message");
        }
        return BottomNavigation.getInstance().getNavigationBarManager();
    }

    public static BottomNavigation getInstance() {
        if (getInstance == null) {
            synchronized (BottomNavigation.class) {
                if (getInstance == null) {
                    getInstance = new BottomNavigation();
                }
            }
        }
        return getInstance;
    }

    private NavigationBar getNavigationBarManager() {
        return new NavigationBar();
    }
}