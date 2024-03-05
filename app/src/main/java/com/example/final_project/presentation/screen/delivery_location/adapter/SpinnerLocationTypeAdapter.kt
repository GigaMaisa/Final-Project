package com.example.final_project.presentation.screen.delivery_location.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.final_project.R
import com.example.final_project.databinding.RecyclerLocationTypeItemBinding
import com.example.final_project.presentation.model.delivery_location.LocationType

class SpinnerLocationTypeAdapter: BaseAdapter() {

    val items = listOf(
        LocationType(R.drawable.ic_apartment, R.string.apartment),
        LocationType(R.drawable.ic_house, R.string.house),
        LocationType(R.drawable.ic_office, R.string.office),
        LocationType(R.drawable.ic_other, R.string.other)
    )

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = RecyclerLocationTypeItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        with(view) {
            imageViewIcon.setImageResource(items[position].icon)
            tvText.text = parent!!.context.resources.getString(items[position].text)
        }
        return view.root
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}