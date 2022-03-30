package com.google.newspaper.utils

import android.app.Activity
import android.util.DisplayMetrics
import android.view.WindowInsets

import android.os.Build
import kotlin.math.ceil

class DpToPx {
    fun dpToPx(dp: Int, activity: Activity): Int {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            ceil((dp * displayMetrics.density).toDouble()).toInt()
        }
    }
}