package com.abhay.tataaigclaim.label_edittext

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.abhay.tataaigclaim.R
import com.abhay.tataaigclaim.datetime_edittext.ClaimAssuredDateTimeEditText
import com.abhay.tataaigclaim.hint_edittext.ClaimAssuredHintEditText
import com.abhay.tataaigclaim.phone_edittext.ClaimAssuredPhoneEditText
import com.abhay.tataaigclaim.spinner_edittext.ClaimAssuredSpinnerEditText

class ClaimAssuredLabelEditText : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    private lateinit var labelTextView: TextView
    private var labelText: String? = ""
    private var isRequiredText = "*"
    private var isMandatoryLabel: Boolean = false
    private var showLabel: Boolean = true
    private var hintText: String? = ""
    var isTime: Boolean = false
    var isDate: Boolean = false
    lateinit var spinnerEditText: ClaimAssuredSpinnerEditText
    lateinit var dateTimeEditText: ClaimAssuredDateTimeEditText
    lateinit var phoneEditText: ClaimAssuredPhoneEditText
    lateinit var standardEditText: ClaimAssuredHintEditText

    enum class EditTextType {
        STANDARD, PHONE, DATETIME, SPINNER
    }

    private var editTextType = EditTextType.STANDARD


    fun init(context: Context, attrs: AttributeSet) {
        orientation = VERTICAL // Set orientation to vertical
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ClaimAssuredLabelEditText)

        isMandatoryLabel =
            typedArray.getBoolean(R.styleable.ClaimAssuredLabelEditText_isMandatoryLabel, false)
        showLabel = typedArray.getBoolean(R.styleable.ClaimAssuredLabelEditText_showLabel, true)
        labelText = typedArray.getString(R.styleable.ClaimAssuredLabelEditText_labelText)
        hintText = typedArray.getString(R.styleable.ClaimAssuredLabelEditText_hintText)
        isTime = typedArray.getBoolean(R.styleable.ClaimAssuredLabelEditText_isTime, false)
        isDate = typedArray.getBoolean(R.styleable.ClaimAssuredLabelEditText_isDate, false)
        val its = EditTextType.values()
        editTextType = its[typedArray.getInt(R.styleable.ClaimAssuredLabelEditText_editTextType, 0)]

        typedArray.recycle()

        spinnerEditText = ClaimAssuredSpinnerEditText(context, attrs)
        phoneEditText = ClaimAssuredPhoneEditText(context, attrs)
        dateTimeEditText = ClaimAssuredDateTimeEditText(context, attrs)
        standardEditText = ClaimAssuredHintEditText(context, attrs)
        // Create prefix text view with common styles
        if (showLabel) {
            val originalText = labelText
            val textToAdd = isRequiredText
            val finalText = originalText + textToAdd
            val spannableString = SpannableString(finalText)
            val startIndex = finalText.length - textToAdd.length // Get starting index of added text
            val endIndex = finalText.length
            spannableString.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        context,
                        R.color.labelMandatoryTextColor
                    )
                ), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            labelTextView = TextView(context).apply {
                layoutParams =
                    LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                text = if (isMandatoryLabel) {
                    spannableString
                } else {
                    originalText
                }

                textSize = 13f
                typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
                gravity = Gravity.CENTER_VERTICAL
                textAlignment = TEXT_ALIGNMENT_TEXT_START
                setTextColor(ContextCompat.getColor(context, R.color.labelTextColor))
            }
            addView(labelTextView)
        }
        when (editTextType) {
            EditTextType.STANDARD -> {
                addView(standardEditText.apply {
                    /*if (!hintText.isNullOrEmpty()) {
                        setHint(hintText)
                    }*/
                })

            }

            EditTextType.PHONE -> {
                addView(phoneEditText)
            }

            EditTextType.DATETIME -> {
                addView(dateTimeEditText)
            }

            EditTextType.SPINNER -> {
                addView(spinnerEditText)
            }
        }

    }
}