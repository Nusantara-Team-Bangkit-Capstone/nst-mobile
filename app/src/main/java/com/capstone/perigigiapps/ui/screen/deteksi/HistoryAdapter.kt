package com.capstone.perigigiapps.ui.screen.deteksi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.perigigiapps.databinding.ItemCardProfileBinding
import com.capstone.perigigiapps.network.response.deteksi.DataItem

class HistoryAdapter(private val listHistory: List<DataItem?>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCardProfileBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val history = listHistory[position]
        val id = history?.id
        val title = history?.scanningResult
        val description = history?.scanningDate
        val photo = history?.toothImg
        viewHolder.binding.tvItemName.text = title
        viewHolder.binding.tvItemDescription.text = description
        Glide.with(viewHolder.itemView.context)
            .load(photo)
            .into(viewHolder.binding.ivItemPhoto)
//        viewHolder.itemView.setOnClickListener {
//            val intentDetail = Intent(viewHolder.itemView.context, HomeFragment::class.java)
//            intentDetail.putExtra(DetailFragment.id, id)
//            viewHolder.itemView.context.startActivity(intentDetail)
//        }
    }

    override fun getItemCount() = listHistory.size

    class ViewHolder(var binding: ItemCardProfileBinding) : RecyclerView.ViewHolder(binding.root)
}