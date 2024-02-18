package com.example.final_project.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.ViewPagerOfferItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.Offer

class OffersViewPagerAdapter :
    ListAdapter<Offer, OffersViewPagerAdapter.OfferViewHolder>(OffersItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder(
            ViewPagerOfferItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind()
    }

    inner class OfferViewHolder(private val binding: ViewPagerOfferItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val offer = currentList[adapterPosition]
            with(binding) {
                ivCover.loadImage(offer.cover)
                tvSpecialOffer.text = offer.title
            }
        }
    }

    companion object {
        private val OffersItemDiffCallback = object : DiffUtil.ItemCallback<Offer>() {

            override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem == newItem
            }
        }
    }
}