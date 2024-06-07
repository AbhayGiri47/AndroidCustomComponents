package com.abhay.tataaigclaim.label_edittext

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.abhay.tataaigclaim.R
import com.abhay.tataaigclaim.hint_edittext.ClaimAssuredHintEditText
import com.abhay.tataaigclaim.phone_edittext.ClaimAssuredPhoneEditText
import com.abhay.tataaigclaim.spinner_edittext.ClaimAssuredSpinnerEditText

class ClaimAssuredLabelEditText(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var labelTextView: TextView
    private var labelText: String? = ""
    private var isRequiredText = "*"
    private val isMandatoryLabel: Boolean
    private val showLabel: Boolean

    enum class EditTextType {
        STANDARD, PHONE, DATETIME, SPINNER
    }

    private var editTextType = EditTextType.STANDARD


    init {
        orientation = VERTICAL // Set orientation to horizontal
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ClaimAssuredLabelEditText)

        isMandatoryLabel =
            typedArray.getBoolean(R.styleable.ClaimAssuredLabelEditText_isMandatoryLabel, false)
        showLabel = typedArray.getBoolean(R.styleable.ClaimAssuredLabelEditText_showLabel, true)
        labelText = typedArray.getString(R.styleable.ClaimAssuredLabelEditText_labelText)
        val its = EditTextType.values()
        editTextType = its[typedArray.getInt(R.styleable.ClaimAssuredLabelEditText_editTextType, 0)]

        typedArray.recycle()

        // Create prefix text view with common styles
        if (showLabel) {
            val originalText = labelText
            val textToAdd = isRequiredText
            val finalText = originalText + textToAdd
            val spannableString = SpannableString(finalText)
            val startIndex = finalText.length - textToAdd.length // Get starting index of added text
            val endIndex = finalText.length
            spannableString.setSpan(ForegroundColorSpan(Color.RED), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val labelFinalText = spannableString
            labelTextView = TextView(context).apply {
                layoutParams =
                    LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                text = if (isMandatoryLabel){
                    labelFinalText
                }else{
                    originalText
                }

                textSize = 13f
                typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
                gravity = Gravity.CENTER_VERTICAL
                textAlignment = TEXT_ALIGNMENT_TEXT_START
            }
            addView(labelTextView)
        }
        when (editTextType) {
            EditTextType.STANDARD -> {
                addView(ClaimAssuredHintEditText(context, attrs))
            }

            EditTextType.PHONE -> {
                addView(ClaimAssuredPhoneEditText(context, attrs))
            }

            EditTextType.DATETIME -> {
                addView(ClaimAssuredHintEditText(context, attrs))

            }

            EditTextType.SPINNER -> {
                addView(ClaimAssuredSpinnerEditText(context, attrs))
            }
        }

    }
}