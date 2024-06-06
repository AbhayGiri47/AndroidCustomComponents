package com.abhay.tataaigclaim.hint_edittext

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.LayerDrawable
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.abhay.tataaigclaim.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ClaimAssuredHintEditText : TextInputEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        setHintTextColor(ContextCompat.getColor(context, R.color.hintTextColor))
        background = LayerDrawable(
            arrayOf(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.outline_background,
                    context.theme
                )
            )
        )
        setPadding(resources.getDimensionPixelSize(R.dimen.dimen_20dp), 0, resources.getDimensionPixelSize(R.dimen.dimen_8dp), 0)
        textSize = 14f
        gravity = Gravity.CENTER_VERTICAL
        textAlignment = TEXT_ALIGNMENT_TEXT_START
        typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
    }

    override fun getText(): Editable? {
        val text = super.getText()
        if (!text.isNullOrEmpty())
            return text
        if (!hint.isNullOrEmpty() && Thread.currentThread().stackTrace[3].className == TextInputLayout::class.qualifiedName)
            return SpannableStringBuilder(hint)
        return text
    }
}
