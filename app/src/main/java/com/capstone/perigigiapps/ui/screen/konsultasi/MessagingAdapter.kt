package com.capstone.perigigiapps.ui.screen.konsultasi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.perigigiapps.databinding.ItemMessageBinding
import com.capstone.perigigiapps.network.response.messaging.DataItem

class MessagingAdapter : ListAdapter<DataItem, MessagingAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMessageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val chat = getItem(position)
        val name = chat?.receiverName
        val message = chat?.message
        val date = chat?.createdAt
        viewHolder.binding.tvMessenger.text = name
        viewHolder.binding.tvMessage.text = message
        viewHolder.binding.tvTimestamp.text = date
    }


    class ViewHolder(var binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}