package com.abhay.tataaigclaim

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat


class ConsumerButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
    var isPrimary: Boolean = true
        set(value) {
            field = value
            invalidate()
        }
    @ColorRes
    private var btnBgColor: Int = R.color.colorAccent
    @ColorRes
    private var btnShadowColor: Int = R.color.sapphire_50
    @DimenRes
    private var btnElevation: Int = R.dimen.elevation
    init {
        val typedArr =
            context.theme.obtainStyledAttributes(
                attrs, R.styleable.ConsumerButton, 0, 0
            )
        for (i in 0 until typedArr.indexCount) {
            when (typedArr.getIndex(i)) {
                R.styleable.ConsumerButton_is_primary -> {
                    isPrimary = typedArr.getBoolean(
                        R.styleable.ConsumerButton_is_primary, true
                    )
                }
                R.styleable.ConsumerButton_cb_shadow_color -> {
                    btnShadowColor = typedArr.getResourceId(
                        R.styleable.ConsumerButton_cb_shadow_color,
                        R.color.colorAccent
                    )
                }
                R.styleable.ConsumerButton_elevation -> {
                    btnElevation = typedArr.getResourceId(
                        R.styleable.ConsumerButton_elevation,
                        R.dimen.elevation
                    )
                }
            }
        }
        initBackground()
    }
    private fun initBackground() {
        kotlin.runCatching {
            btnBgColor = if (isPrimary) R.color.colorAccent else R.color.white
            if (!isEnabled) btnBgColor = R.color.disabled
            var textColor = if (isPrimary) R.color.white else R.color.colorAccent
            if (!isEnabled) textColor = R.color.cobalt_45
            val finalTextColor = ContextCompat.getColor(context, textColor)
            setTextColor(finalTextColor)
            typeface = ResourcesCompat.getFont(context,R.font.poppins_medium)
            gravity = Gravity.CENTER
            val shadowColor = if (isEnabled) btnShadowColor else android.R.color.transparent
            textSize = when (isPrimary) {
                true -> 16F
                false -> 14F
            }
            background = when (isPrimary) {
                true -> ViewUtils.generateBackgroundWithShadow(
                    this,
                    btnBgColor,
                    R.dimen.radius_corner,
                    shadowColor,
                    btnElevation,
                    Gravity.CENTER
                )
                false -> null
            }

        }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initBackground()
    }
}