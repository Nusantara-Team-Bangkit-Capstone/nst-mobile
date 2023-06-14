package com.capstone.perigigiapps.ui.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.perigigiapps.databinding.ItemArticleBigBinding
import com.capstone.perigigiapps.network.response.ArticlesItem

class BigArticleAdapter(private val listArticle: List<ArticlesItem>) :
    RecyclerView.Adapter<BigArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemArticleBigBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val article = listArticle[position]
        val id = article.source?.id.toString()
        val title = article.title
        val description = article.description
        val photo = article.urlToImage
        viewHolder.binding.tvItemName.text = title
        Glide.with(viewHolder.itemView.context)
            .load(photo)
            .into(viewHolder.binding.ivItemPhoto)
//        viewHolder.itemView.setOnClickListener {
//            val intentDetail = Intent(viewHolder.itemView.context, HomeFragment::class.java)
//            intentDetail.putExtra(DetailFragment.id, id)
//            viewHolder.itemView.context.startActivity(intentDetail)
//        }
    }

    override fun getItemCount() = listArticle.size

    class ViewHolder(var binding: ItemArticleBigBinding) : RecyclerView.ViewHolder(binding.root)
}