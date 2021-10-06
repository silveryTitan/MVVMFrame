package com.silverytitan.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.silverytitan.mvvmframe.expand.showToast
import com.silverytitan.mvvmframe.utils.BottomNavigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BottomNavigation.with(this)
            .setPage(
                mutableListOf(
                    R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher
                )
            ).setOnTouchClickListener {
                "点击了$it".showToast()
            }.setOnTouchMoveListener {
                "移动了$it".showToast()
            }.setOnTouchLongListener {
                "长按了$it".showToast()
            }.show()
    }
}

fun main(args: Array<String>) {
    print(6.08f.toInt())
}