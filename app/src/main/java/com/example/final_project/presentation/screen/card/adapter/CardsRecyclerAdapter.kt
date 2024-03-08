package com.example.final_project.presentation.screen.card.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.databinding.CardItemBinding
import com.example.final_project.databinding.FragmentCardBinding
import com.example.final_project.presentation.model.card.Card
import com.example.final_project.presentation.model.cart.CartItem

class CardsRecyclerAdapter : ListAdapter<Card, CardsRecyclerAdapter.CardViewHolder>(CardsItemDiffCallback) {

    inner class CardViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() = with(binding) {
            val card = getItem(adapterPosition)

            if (card.cardNumber.startsWith("4")) {
                Log.d("RACXA TYPE", "VIZAAA")
                ivCardManufacturer.setImageResource(R.drawable.iv_visa)
            } else if (card.cardNumber.startsWith("5")) {
                Log.d("RACXA TYPE", "MASTERCARD")
                ivCardManufacturer.setImageResource(R.drawable.iv_mastercard_logo)
            }

            tvCardName.text = card.cardName
            tvCardNumber.text = "**** **** **** ${card.cardNumber.takeLast(4)}"
        }
    }

    companion object {
        private val CardsItemDiffCallback = object : DiffUtil.ItemCallback<Card>() {

            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind()
    }
}