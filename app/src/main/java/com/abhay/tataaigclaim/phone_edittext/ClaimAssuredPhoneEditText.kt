package com.abhay.tataaigclaim.phone_edittext

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.abhay.tataaigclaim.R
import com.google.android.material.textfield.TextInputEditText

class ClaimAssuredPhoneEditText(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {

    private var prefixTextView: TextView
    private var phoneEditText: TextInputEditText


    init {
        orientation = HORIZONTAL // Set orientation to horizontal
        background = LayerDrawable(
            arrayOf(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.outline_background,
                    context.theme
                )
            )
        )

        // Create prefix text view with common styles
        prefixTextView = TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                    setMargins(resources.getDimensionPixelSize(R.dimen.dimen_20dp), 0, 0, 0)
                }
            text = ContextCompat.getString(context,R.string._91)
            textSize = 14f
            typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
            gravity = Gravity.CENTER_VERTICAL
            textAlignment = TEXT_ALIGNMENT_TEXT_START
        }

        // Create phone edit text with common styles and input type
        phoneEditText = TextInputEditText(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            background = null
            inputType = InputType.TYPE_CLASS_PHONE
            textSize = 14f
            typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
            gravity = Gravity.CENTER_VERTICAL
            textAlignment = TEXT_ALIGNMENT_TEXT_START

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Not needed for this case
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length ?: 0 > 10) {
                        val limitedString = s.toString().substring(0, 10)
                        this@apply.setText(limitedString)
                        this@apply.setSelection(limitedString.length) // Set cursor position
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed for this case
                }
            })

        }

        addView(prefixTextView)
        addView(phoneEditText)
    }

    // Getter method to access EditText value
    fun getNumberEditTextValue(): String {
        return phoneEditText.text.toString()  // Get text and convert to string
    }
}