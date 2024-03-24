package com.example.final_project.presentation.screen.delivery_location_add.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.final_project.R
import com.example.final_project.databinding.RecyclerAddressTypeItemBinding
import com.example.final_project.presentation.model.delivery_location.AddressType

class AddressTypeRecyclerAdapter :
    ListAdapter<AddressType, AddressTypeRecyclerAdapter.AddressTypeViewHolder>(
        AddressTypeItemDiffCallback
    ) {

    var onAddressClick: ((AddressType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressTypeViewHolder {
        return AddressTypeViewHolder(
            RecyclerAddressTypeItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AddressTypeViewHolder, position: Int) {
        holder.bind()
    }

    inner class AddressTypeViewHolder(private val binding: RecyclerAddressTypeItemBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            val addressType = currentList[adapterPosition]
            with(binding) {
//                if (addressType.isSelected)
//                    onAddressClick?.invoke(addressType)
                if (addressType.isSelected) {
                    imageViewIcon.setImageResource(addressType.selectedIcon)
                    containerAddressType.setBackgroundResource(R.drawable.selected_background)
                } else {
                    imageViewIcon.setImageResource(addressType.icon)
                    containerAddressType.setBackgroundResource(R.drawable.edit_text_background)
                }
                tvText.text = itemView.context.resources.getString(addressType.text)
                root.setOnClickListener {
                    onAddressClick?.invoke(addressType)
                }
            }
        }
    }

    companion object {
        private val AddressTypeItemDiffCallback = object : DiffUtil.ItemCallback<AddressType>() {

            override fun areItemsTheSame(oldItem: AddressType, newItem: AddressType): Boolean {
                return oldItem.icon == newItem.icon
            }

            override fun areContentsTheSame(oldItem: AddressType, newItem: AddressType): Boolean {
                return oldItem == newItem
            }
        }
    }
}