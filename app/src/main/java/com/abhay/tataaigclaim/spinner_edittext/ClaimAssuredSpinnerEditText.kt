package com.abhay.tataaigclaim.spinner_edittext

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.abhay.tataaigclaim.R
import com.abhay.tataaigclaim.Utils.dpToPx


class ClaimAssuredSpinnerEditText(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {

    private val imageView: ImageView
    private val spinnerAdapter: SpinnerAdapter
    private val spinner: Spinner

    init {
        orientation = HORIZONTAL
        background = LayerDrawable(
            arrayOf(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.outline_background,
                    context.theme
                )
            )
        )

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_spinner_drop_down, this, true)
        imageView = view.findViewById(R.id.ivDropDown)
        spinner = view.findViewById(R.id.spinnerDropDown)
        spinnerAdapter = SpinnerAdapter(context)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            private var previouslySelectedItem = -1

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                view?.findViewById<ImageView>(R.id.ivSpinner)?.visibility = View.GONE
                val textView = view?.findViewById<TextView>(R.id.tvSpinner) ?: return

                val params = textView.layoutParams as MarginLayoutParams
                val marginInDp = 20 // Adjust as needed
                params.marginStart = marginInDp.dpToPx(resources)
                textView.layoutParams = params

                previouslySelectedItem = if (position != previouslySelectedItem) position else -1
                spinnerAdapter.setPreviouslySelectedItem(previouslySelectedItem)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // No action needed here
            }
        }

    }

    fun setSpinnerData(items: List<SpinnerDataModel>) {
        spinnerAdapter.addData(items)
        spinner.adapter = spinnerAdapter

    }

    fun getSpinnerText(): String {
        return spinnerAdapter.getItem(spinner.selectedItemPosition).text
    }
}