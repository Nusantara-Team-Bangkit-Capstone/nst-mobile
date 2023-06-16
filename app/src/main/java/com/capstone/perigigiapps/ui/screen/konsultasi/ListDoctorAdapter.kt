package com.capstone.perigigiapps.ui.screen.konsultasi

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.perigigiapps.R
import com.capstone.perigigiapps.databinding.ItemListDoctorBinding
import com.capstone.perigigiapps.network.response.doctor.DataItem

class ListDoctorAdapter(private val listDoctor: List<DataItem?>?) :
    RecyclerView.Adapter<ListDoctorAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListDoctorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val doctor = listDoctor?.get(position)
        val id = doctor?.id
        val nama = doctor?.nama
        val alamat = doctor?.alamat
        val photo = doctor?.foto
        viewHolder.binding.tvItemName.text = nama
        viewHolder.binding.tvItemAddress.text = alamat
        viewHolder.binding.tvItemStatus.text = "Online"
        if (photo != "") {
            Glide.with(viewHolder.itemView.context)
                .load(photo)
                .into(viewHolder.binding.ivItemPhoto)
        } else {
            Glide.with(viewHolder.itemView.context)
                .load(R.drawable.avatar_male)
                .into(viewHolder.binding.ivItemPhoto)
        }
        viewHolder.binding.tvButtonMessaging.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, MessagingActivity::class.java)
            intentDetail.putExtra(MessagingActivity.nameReceiver, nama.toString())
            intentDetail.putExtra(MessagingActivity.idReceiver, id.toString())
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listDoctor?.size!!

    class ViewHolder(var binding: ItemListDoctorBinding) : RecyclerView.ViewHolder(binding.root)
}