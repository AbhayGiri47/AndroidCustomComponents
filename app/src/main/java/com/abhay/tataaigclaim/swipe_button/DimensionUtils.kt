package com.abhay.tataaigclaim.swipe_button

import android.content.Context


internal object DimentionUtils {
    fun converPixelsToSp(px: Float, context: Context): Float {
        return px / context.resources.displayMetrics.scaledDensity
    }
}