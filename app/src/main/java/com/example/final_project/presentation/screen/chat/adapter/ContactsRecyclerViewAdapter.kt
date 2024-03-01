package com.example.final_project.presentation.screen.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.ContactRecyclerItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.Contact

class ContactsRecyclerViewAdapter: ListAdapter<Contact, ContactsRecyclerViewAdapter.ContactViewHolder>(ContactItemDiffCallback) {

    var onContactClick: ((Contact) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(ContactRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
       holder.bind()
    }

    inner class ContactViewHolder(private val binding: ContactRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind() {
            val contact = currentList[adapterPosition]
            with(binding) {
                contact.imgUrl?.let {
                    shapeableImageViewCover.loadImage(it)
                }
                tvFullName.text = contact.fullName

                root.setOnClickListener {
                    onContactClick?.invoke(contact)
                }
            }
        }
    }

    companion object {
        private val ContactItemDiffCallback = object : DiffUtil.ItemCallback<Contact>() {

            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.uuid == newItem.uuid
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }
        }
    }
}