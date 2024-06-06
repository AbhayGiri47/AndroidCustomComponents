package com.abhay.tataaigclaim.spinner_edittext

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.abhay.tataaigclaim.R

/*class ClaimAssuredSpinnerEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundDrawable: Drawable?
    private val spinner: Spinner

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSpinnerView)
        backgroundDrawable = ResourcesCompat.getDrawable(resources, R.drawable.outline_background, context.theme)

        val spinnerEntries = typedArray.getTextArrayOrThrow(R.styleable.CustomSpinnerView_spinnerEntries)
            ?.toMutableList() ?: mutableListOf()

        typedArray.recycle()

        // Create and configure Spinner
        spinner = Spinner(context)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerEntries)
        spinner.adapter = spinnerAdapter

        // Set background drawable (optional)
        background = LayerDrawable(arrayOf(backgroundDrawable))
    }

    // Optional getter for Spinner
    fun getSpinner(): Spinner {
        return spinner
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val viewWidth = right - left
        val viewHeight = bottom - top

        // Set Spinner size to match view dimensions
        val spinnerParams = ViewGroup.LayoutParams(viewWidth, viewHeight)
        spinner.layoutParams = spinnerParams
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background drawable (if available)
        backgroundDrawable?.setBounds(0, 0, width, height)
        backgroundDrawable?.draw(canvas)

       *//* // Position and draw Spinner (adjust as needed)
        val spinnerWidth = resources.getDimensionPixelSize(R.dimen.spinner_width) // Use resource for width
        val spinnerHeight = resources.getDimensionPixelSize(R.dimen.spinner_height) // Use resource for height
        val spinnerPadding = resources.getDimensionPixelSize(R.dimen.spinner_padding) // Use resource for padding

        val spinnerParams = ViewGroup.LayoutParams(spinnerWidth, spinnerHeight).apply {

        }
        spinnerParams.setMargins(spinnerPadding, spinnerPadding, spinnerPadding, spinnerPadding)
        spinner.layoutParams = spinnerParams

        spinner.layout(
            spinnerPadding,
            spinnerPadding,
            spinnerWidth + spinnerPadding,
            spinnerHeight + spinnerPadding
        )
        spinner.draw(canvas)*//*
    }
}*/

class ClaimAssuredSpinnerEditText(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private var spinnerData = mutableListOf<SpinnerDataModel>()

    private val textView: TextView
    private val imageView: ImageView
    private val recyclerView: RecyclerView
    private val spinnerItemAdapter: SpinnerItemAdapter
    private val spinnerAdapter: SpinnerAdapter
    private val spinner: Spinner

    init {
        orientation = VERTICAL
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

        textView = view.findViewById(R.id.tvSpinnerText)
        imageView = view.findViewById(R.id.ivDropDown)
        recyclerView = view.findViewById(R.id.rvSpinnerItems)
        spinner = view.findViewById(R.id.spinnerDropDown)
         spinnerAdapter = SpinnerAdapter(context)
        spinner.adapter = spinnerAdapter

        // Set your adapter for recycler view (replace with your data)
        spinnerItemAdapter = SpinnerItemAdapter(context)
        recyclerView.adapter = spinnerItemAdapter
        recyclerView.visibility = View.GONE
        spinner.visibility = View.GONE

        setOnClickListener {

            /*val isVisible = recyclerView.visibility == View.VISIBLE
            recyclerView.visibility = if (isVisible) View.GONE else View.VISIBLE*/

           /* val isVisible = spinner.visibility == View.VISIBLE
            spinner.visibility = if (isVisible) View.GONE else View.VISIBLE
            Log.d("TAG", "Spinner CLick:  $isVisible")*/

            vCountry.setOnClickListener {
                popupWindow?.dismiss()
                if (popupWindow == null) {
                    provideCountryPopupWindow(it)
                }
                popupWindow!!.showAsDropDown(it, 0, -it.height)
            }

        }

    }

    private fun provideCountryPopupWindow(it: View) {
        popupWindow = PopupWindow(it.width, ViewGroup.LayoutParams.WRAP_CONTENT)
            .apply {
                val backgroundDrawable = activity!!.getDrawable(
                    R.drawable.blue_outline_white_background)
                    .apply {  }
                setBackgroundDrawable(backgroundDrawable)
                isOutsideTouchable = true
                val listView = layoutInflater.inflate(
                    R.layout.layout_country_dropdown,
                    null,
                    false) as ListView
                listView.adapter = countryAdapter
                listView.setOnItemClickListener { _, _, position, _ ->
                    val selectedCountry = countryAdapter.getItem(position)!!
                    viewModel.setLegalCountry(selectedCountry)
                    popupWindow?.dismiss()
                }
                contentView = listView
            }
    }

    fun setSpinnerData(items: List<SpinnerDataModel>){
        spinnerData = items.toMutableList()
        textView.text = spinnerData[0].text
        spinnerItemAdapter.addData(items)
        spinnerAdapter.addData(items)

    }

    fun getSpinnerText():String{
        return textView.text.toString()
    }

    inner class SpinnerItemAdapter(context: Context) :
        RecyclerView.Adapter<SpinnerItemAdapter.ViewHolder>() {

        private val inflater = LayoutInflater.from(context)

        private val data = mutableListOf<SpinnerDataModel>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.layout_spinner_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.apply {
                textSize = 14f
                typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
            }
            holder.textView.text = data[position].text
            imageView.setImageResource(data[position].imageResourceId)
            holder.itemView.setOnClickListener {
                // Update text view with selected data and hide recycler view
                textView.text = data[position].text
                recyclerView.visibility = View.GONE
            }
        }

        override fun getItemCount() = data.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.tvSpinner)
            val imageView = itemView.findViewById<ImageView>(R.id.ivSpinner)
        }

        fun addData(items: List<SpinnerDataModel>) {
            data.addAll(items) // Add new items to the list
            notifyDataSetChanged() // Notify adapter about data change
        }
    }
}