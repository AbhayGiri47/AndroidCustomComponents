package com.abhay.tataaigclaim.spinner_edittext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.abhay.tataaigclaim.R

class SpinnerAdapter(context: Context) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)
    private val items = mutableListOf<SpinnerDataModel>()

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): SpinnerDataModel = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.layout_spinner_item, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.ivSpinner)
        val textView = view.findViewById<TextView>(R.id.tvSpinner).apply {
            textSize = 14f
            typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)

        }
        val item = getItem(position)
        // Set image drawable for the item
        imageView.setImageResource(item.imageResourceId)
        // Set text for the item
        textView.text = item.text

        return view
    }

    fun addData(items: List<SpinnerDataModel>) {
        this.items.addAll(items) // Add new items to the list
        notifyDataSetChanged() // Notify adapter about data change
    }
}