package com.example.final_project.presentation.screen.delivery_location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.databinding.RecyclerDeliveryLocationItemBinding
import com.example.final_project.presentation.model.delivery_location.DeliveryLocation


class DeliveryLocationsRecyclerAdapter: ListAdapter<DeliveryLocation, DeliveryLocationsRecyclerAdapter.DeliveryLocationViewHolder>(DeliveryLocationItemDiffCallback) {

    var onDeleteClick: ((DeliveryLocation) -> Unit)? = null
    var onSelectClick: ((DeliveryLocation) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryLocationViewHolder {
        return DeliveryLocationViewHolder(
            RecyclerDeliveryLocationItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DeliveryLocationViewHolder, position: Int) {
        holder.bind()
    }

    inner class DeliveryLocationViewHolder(private val binding: RecyclerDeliveryLocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val currentLocation = currentList[adapterPosition]
            ImageViewAddressType.setImageResource(currentLocation.addressType.icon)
            tvLocationName.text = currentLocation.locationName

            if (currentLocation.isDefault) {
                root.setBackgroundResource(R.drawable.btn_light_green_background)
            } else {
                root.setBackgroundResource(R.drawable.edit_text_background)
            }

            imageBtnRemoveLocation.setOnClickListener {
                if (!currentLocation.isDefault)
                    onDeleteClick?.invoke(currentLocation)
            }

            root.setOnClickListener {
                onSelectClick?.invoke(currentLocation)
            }
        }
    }

    companion object {
        private val DeliveryLocationItemDiffCallback = object : DiffUtil.ItemCallback<DeliveryLocation>() {

            override fun areItemsTheSame(oldItem: DeliveryLocation, newItem: DeliveryLocation): Boolean {
                return oldItem.location == newItem.location
            }

            override fun areContentsTheSame(oldItem: DeliveryLocation, newItem: DeliveryLocation): Boolean {
                return oldItem == newItem
            }
        }
    }
}