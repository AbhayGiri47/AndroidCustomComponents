package com.abhay.tataaigclaim

import android.content.res.Resources

object Utils {

    fun Int.dpToPx(resources: Resources): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }
}