package com.example.hungryguys.utills

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat

// Activity와 종속적인 것들
class ActivityUtills(private val activity: Activity) {

    // 상태바 투명하게 하는 함수
    fun setStatusBarTransparent() {
        activity.apply {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            // API 30 에 적용
            if(Build.VERSION.SDK_INT >= 30) {
                WindowCompat.setDecorFitsSystemWindows(window, false)
            }
        }
    }

    // 상태바 투명화 Padding 보정 함수
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    fun setStatusBarPadding(viewGroup: ViewGroup) {
        activity.apply {
            val statusbarId = resources.getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight = resources.getDimensionPixelSize(statusbarId)

            val navigationbarId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            val navigationHeight = resources.getDimensionPixelSize(navigationbarId)

            viewGroup.setPadding(0, statusBarHeight, 0, navigationHeight)
        }
    }

    // 알림 설정 함수
    fun setNotification(value: Boolean) {
        if (value) {
            Log.d("설정", "알림 활성화")
        } else {
            Log.d("설정", "알림 비활성화")
        }
    }

    // 다크모드 설정 함수
    fun setDarkmode(value: Boolean) {
        activity.apply {
            if (value) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}