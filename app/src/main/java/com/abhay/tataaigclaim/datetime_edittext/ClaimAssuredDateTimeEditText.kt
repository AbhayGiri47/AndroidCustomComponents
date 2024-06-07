package com.abhay.tataaigclaim.datetime_edittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.abhay.tataaigclaim.R
import com.abhay.tataaigclaim.Utils.dpToPx

class ClaimAssuredDateTimeEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {

    private var isTime: Boolean
    private var isDate: Boolean
    private val outlineBackground: Drawable?

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClaimAssuredLabelEditText)

        isTime = typedArray.getBoolean(R.styleable.ClaimAssuredLabelEditText_isTime, false)
        isDate = typedArray.getBoolean(R.styleable.ClaimAssuredLabelEditText_isDate, false)
        outlineBackground = ResourcesCompat.getDrawable(resources, R.drawable.outline_background, context.theme)

        typedArray.recycle()

        // Set common properties
        layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                48.dpToPx(resources)
            )
        background = LayerDrawable(arrayOf(outlineBackground))
        textSize = 14f
        gravity = Gravity.CENTER_VERTICAL
        textAlignment = TEXT_ALIGNMENT_TEXT_START
        setPadding(
            resources.getDimensionPixelSize(R.dimen.dimen_20dp),
            0,
            resources.getDimensionPixelSize(R.dimen.dimen_8dp),
            0
        )
        typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
        isFocusable = false

        // Set end icon and text based on attributes
        if (isDate) {
            setEndIconDrawable(ContextCompat.getDrawable(context, R.drawable.calendar))
            setText(ContextCompat.getString(context, R.string.dd_mm_yyyy))
        } else if (isTime) {
            setEndIconDrawable(ContextCompat.getDrawable(context, R.drawable.time))
            setText(ContextCompat.getString(context, R.string._15_28_pm))
        }
    }

    private fun setEndIconDrawable(drawable: Drawable?) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            setCompoundDrawablesRelative(
                null,
                null,
                drawable,
                null
            ) // Set drawable on the right side
        } else {
            setCompoundDrawablesRelative(null, null, null, null) // Remove drawable
        }
    }

     fun setDateTimeValue(date:Boolean,time:Boolean){
        isDate = date
        isTime = time
    }
}