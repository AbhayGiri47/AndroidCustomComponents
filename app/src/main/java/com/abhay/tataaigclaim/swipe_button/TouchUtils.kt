package com.abhay.tataaigclaim.swipe_button

import android.view.MotionEvent
import android.view.View


internal object TouchUtils {
    fun isTouchOutsideInitialPosition(event: MotionEvent, view: View): Boolean {
        return event.x > view.x + view.width
    }
}