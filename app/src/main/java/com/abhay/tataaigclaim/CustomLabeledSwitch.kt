package com.abhay.tataaigclaim

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

interface ToggleListener {
    fun onToggle(view: CustomLabeledSwitch, flag: Boolean)
}

class CustomLabeledSwitch : View {
    private var isOn: Boolean = false
    private var vWidth = 0
    private var vHeight = 0
    private var padding = 0
    private var thumbColor = 0
    private var textSize = 0
    private var fontResId = 0
    private var textColorResId = 0
    private var outerRadii = 0
    private var thumbRadii = 0
    private lateinit var paint: Paint
    private var startTime: Long = 0
    private var labelOn: String? = null
    private var labelOff: String? = null
    private var thumbBounds: RectF? = null
    private var thumbOnCenterX = 0f
    private var thumbOffCenterX = 0f
    private var toggleListener: ToggleListener? = null
    private var thumbOnDrawable: Drawable? = null
    private var thumbOffDrawable: Drawable? = null

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        initView()
        initProperties(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context, attrs, defStyle
    ) {
        initView()
        initProperties(attrs)
    }

    private fun initView() {
        labelOn = "YES"
        labelOff = "NO"
        textSize = (12f * resources.displayMetrics.scaledDensity).toInt()
        thumbColor = ContextCompat.getColor(context, R.color.component_colorPrimaryDark)
        paint = Paint()
        paint.isAntiAlias = true
        thumbBounds = RectF()
        thumbOnDrawable = ContextCompat.getDrawable(context, R.drawable.round_switch_thumb_on)
        thumbOffDrawable = ContextCompat.getDrawable(context, R.drawable.round_switch_thumb_off)
    }

    private fun initProperties(attrs: AttributeSet) {
        isEnabled = true
        thumbColor = R.color.component_colorPrimaryDark
        val typedArr =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomLabeledSwitch, 0, 0)
        for (i in 0 until typedArr.indexCount) {
            when (typedArr.getIndex(i)) {
                R.styleable.CustomLabeledSwitch_textOn -> {
                    labelOn = typedArr.getString(R.styleable.CustomLabeledSwitch_textOn)
                }

                R.styleable.CustomLabeledSwitch_textOff -> {
                    labelOff = typedArr.getString(R.styleable.CustomLabeledSwitch_textOff)
                }

                R.styleable.CustomLabeledSwitch_android_fontFamily -> {
                    fontResId = typedArr.getResourceId(
                        R.styleable.CustomLabeledSwitch_android_fontFamily, 0
                    )
                }

                R.styleable.CustomLabeledSwitch_android_textColor -> {
                    textColorResId =
                        typedArr.getColor(R.styleable.CustomLabeledSwitch_android_textColor, 0)
                }

                R.styleable.CustomLabeledSwitch_android_textSize -> {
                    val defaultTextSize = (12f * resources.displayMetrics.scaledDensity).toInt()
                    textSize = typedArr.getDimensionPixelSize(
                        R.styleable.CustomLabeledSwitch_android_textSize, defaultTextSize
                    )
                }

                R.styleable.CustomLabeledSwitch_isOn -> {
                    isOn = typedArr.getBoolean(R.styleable.CustomLabeledSwitch_isOn, false)
                }

                R.styleable.CustomLabeledSwitch_android_enabled -> {
                    isEnabled =
                        typedArr.getBoolean(R.styleable.CustomLabeledSwitch_android_enabled, true)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (fontResId == 0) fontResId = R.font.poppins_semi_bold
        textColorResId = if (isEnabled) {
            R.color.white
        } else {
            R.color.marine_70
        }
        thumbColor = if (isEnabled) {
            if (!isOn) R.color.dark_30 else R.color.cobalt_two
        } else {
            if (!isOn) R.color.dark_20 else R.color.cobalt_two_20
        }
        val switchBackground = if (!isOn) R.drawable.toggle_btn_no else R.drawable.toggle_btn_yes
        setBackgroundResource(switchBackground)
        paint.textSize = textSize.toFloat()
        paint.typeface = ResourcesCompat.getFont(context, fontResId)
        paint.color = textColorResId
// Drawing Switch Labels here
        val maxChar = "N"
        val textCenter = paint.measureText(maxChar) / 2
        if (isOn) {
            val onColor = Color.argb(
                0,
                Color.red(textColorResId),
                Color.green(textColorResId),
                Color.blue(textColorResId)
            )
            paint.color = onColor
            var centerX =
                (width - padding - (padding + (padding ushr 1) + (thumbRadii shl 1)) ushr 1.toFloat()
                    .toInt()).toFloat() - (thumbRadii ushr 1)
            canvas!!.drawText(
                labelOff!!,
                padding + (padding ushr 1) + (thumbRadii shl 1) + centerX - paint.measureText(
                    labelOff
                ) / 2,
                (height ushr 1) + textCenter,
                paint
            )
            paint.color = ContextCompat.getColor(context, textColorResId)
            val maxSize = width - (padding shl 1) - (thumbRadii shl 1)
            centerX = ((padding ushr 1) + maxSize - padding ushr 1.toFloat().toInt()).toFloat()
            canvas.drawText(
                labelOn!!,
                padding + centerX - paint.measureText(labelOn) / 2,
                (height ushr 1) + textCenter,
                paint
            )
        } else {
            val offColor = Color.argb(
                0,
                Color.red(textColorResId),
                Color.green(textColorResId),
                Color.blue(textColorResId)
            )
            paint.color = offColor
            val maxSize = width - (padding shl 1) - (thumbRadii shl 1)
            var centerX = ((padding ushr 1) + maxSize - padding ushr 1.toFloat().toInt()).toFloat()
            canvas!!.drawText(
                labelOn!!,
                padding + centerX - paint.measureText(labelOn) / 2,
                (height ushr 1) + textCenter,
                paint
            )
            paint.color = ContextCompat.getColor(context, textColorResId)
            centerX =
                (width - padding - (padding + (padding ushr 1) + (thumbRadii shl 1)) ushr 1.toFloat()
                    .toInt()).toFloat() - (thumbRadii ushr 1)
            canvas.drawText(
                labelOff!!,
                padding + (padding ushr 1) + (thumbRadii shl 1) + centerX - paint.measureText(
                    labelOff
                ) / 2,
                (height ushr 1) + textCenter,
                paint
            )
        }
// Drawing Switch Thumb here
        /*paint.color = ContextCompat.getColor(context, thumbColor)
        canvas.drawCircle(
            thumbBounds!!.centerX(),
            thumbBounds!!.centerY(),
            thumbRadii.toFloat(),
            paint
        )
        val thumbBitmap = if (isOn) thumbOffDrawable?.toBitmap(2 * thumbRadii, 2 * thumbRadii)
        else thumbOnDrawable?.toBitmap(2 * thumbRadii, 2 * thumbRadii)
        canvas.drawBitmap(
            thumbBitmap!!,
            thumbBounds!!.centerX() - thumbRadii,
            thumbBounds!!.centerY() - thumbRadii,
            paint
        )*/
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = resources.getDimensionPixelSize(R.dimen.switch_labeled_default_width)
        val desiredHeight = resources.getDimensionPixelSize(R.dimen.switch_labeled_default_height)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        vWidth = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> desiredWidth.coerceAtMost(widthSize)
            else -> desiredWidth
        }
        vHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> desiredHeight.coerceAtMost(heightSize)
            else -> desiredHeight
        }
        setMeasuredDimension(vWidth, vHeight)
        outerRadii = vWidth.coerceAtMost(vHeight) ushr 1
        thumbRadii = (vWidth.coerceAtMost(vHeight) / 2.88f).toInt()
        padding = vHeight - thumbRadii ushr 1
        thumbBounds!![vWidth - padding - thumbRadii.toFloat(), padding.toFloat(), vWidth - padding.toFloat()] =
            vHeight - padding.toFloat()
        thumbOnCenterX = thumbBounds!!.centerX()
        thumbBounds!![padding.toFloat(), padding.toFloat(), padding + thumbRadii.toFloat()] =
            vHeight - padding.toFloat()
        thumbOffCenterX = thumbBounds!!.centerX()
        if (isOn) {
            thumbBounds!![vWidth - padding - thumbRadii.toFloat(), padding.toFloat(), vWidth - padding.toFloat()] =
                vHeight - padding.toFloat()
        } else {
            thumbBounds!![padding.toFloat(), padding.toFloat(), padding + thumbRadii.toFloat()] =
                vHeight - padding.toFloat()
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        if (isOn) {
            val switchColor =
                ValueAnimator.ofFloat(width - padding - thumbRadii.toFloat(), padding.toFloat())
            switchColor.apply {
                addUpdateListener { animation: ValueAnimator ->
                    val value = animation.animatedValue as Float
                    thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] =
                        thumbBounds!!.bottom
                    invalidate()
                }
                interpolator = AccelerateDecelerateInterpolator()
                duration = 250
                start()
            }
        } else {
            val switchColor =
                ValueAnimator.ofFloat(padding.toFloat(), width - padding - thumbRadii.toFloat())
            switchColor.apply {
                addUpdateListener { animation: ValueAnimator ->
                    val value = animation.animatedValue as Float
                    thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] =
                        thumbBounds!!.bottom
                    invalidate()
                }
                interpolator = AccelerateDecelerateInterpolator()
                duration = 250
                start()
            }
        }
        isOn = !isOn
        toggleListener?.onToggle(this, isOn)
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (isEnabled) {
            val x = event!!.x
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startTime = System.currentTimeMillis()
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    if (x - (thumbRadii ushr 1) > padding && x + (thumbRadii ushr 1) < width - padding) {
                        thumbBounds!![x - (thumbRadii ushr 1), thumbBounds!!.top, x + (thumbRadii ushr 1)] =
                            thumbBounds!!.bottom
                        invalidate()
                    }
                    true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val endTime = System.currentTimeMillis()
                    val span = endTime - startTime
                    if (span < 200) {
                        performClick()
                    } else {
                        if (x >= width ushr 1) {
                            val switchColor = ValueAnimator.ofFloat(
                                (if (x > width - padding - thumbRadii) (width - padding - thumbRadii).toFloat() else x),
                                width - padding - thumbRadii.toFloat()
                            )
                            switchColor.addUpdateListener { animation: ValueAnimator ->
                                val value = animation.animatedValue as Float
                                thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] =
                                    thumbBounds!!.bottom
                                invalidate()
                            }
                            switchColor.interpolator = AccelerateDecelerateInterpolator()
                            switchColor.duration = 250
                            switchColor.start()
                            isOn = true
                        } else {
                            val switchColor = ValueAnimator.ofFloat(
                                (if (x < padding.toFloat()) padding.toFloat() else x),
                                padding.toFloat()
                            )
                            switchColor.addUpdateListener { animation: ValueAnimator ->
                                val value = animation.animatedValue as Float
                                thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] =
                                    thumbBounds!!.bottom
                                invalidate()
                            }
                            switchColor.interpolator = AccelerateDecelerateInterpolator()
                            switchColor.duration = 250
                            switchColor.start()
                            isOn = false
                        }
                        toggleListener?.onToggle(this, isOn)
                    }
                    invalidate()
                    true
                }

                else -> {
                    super.onTouchEvent(event)
                }
            }
        } else {
            false
        }
    }

    fun setOn(toggleOn: Boolean) {
        isOn = toggleOn
        if (isOn) {
            thumbBounds!![width - padding - thumbRadii.toFloat(), padding.toFloat(), width - padding.toFloat()] =
                height - padding.toFloat()
        } else {
            thumbBounds!![padding.toFloat(), padding.toFloat(), padding + thumbRadii.toFloat()] =
                height - padding.toFloat()
        }
        invalidate()
    }

    fun setOnToggledListener(listener: ToggleListener) {
        toggleListener = listener
    }

    fun initToggleLabels(labelOn: String? = null, labelOff: String? = null) {
        if (!labelOn.isNullOrEmpty()) this.labelOn = labelOn
        if (!labelOff.isNullOrEmpty()) this.labelOff = labelOff
    }
}