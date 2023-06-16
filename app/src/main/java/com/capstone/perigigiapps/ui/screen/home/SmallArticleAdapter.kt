package com.capstone.perigigiapps.ui.screen.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.perigigiapps.databinding.ItemCardProfileBinding
import com.capstone.perigigiapps.network.response.ArticlesItem
import com.capstone.perigigiapps.ui.screen.detail.DetailActivity

class SmallArticleAdapter(private val listArticle: List<ArticlesItem>) :
    RecyclerView.Adapter<SmallArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCardProfileBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val article = listArticle[position]
        val id = article.source?.id.toString()
        val title = article.title
        val description = article.description
        val date = article.publishedAt
        val author = article.author
        val photo = article.urlToImage
        viewHolder.binding.tvItemName.text = title
        viewHolder.binding.tvItemDescription.text = description
        Glide.with(viewHolder.itemView.context)
            .load(photo)
            .into(viewHolder.binding.ivItemPhoto)
        viewHolder.itemView.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.idArticle, id)
            intentDetail.putExtra(DetailActivity.nameArticle, title)
            intentDetail.putExtra(DetailActivity.descriptionArticle, description)
            intentDetail.putExtra(DetailActivity.photoArticle, photo)
            intentDetail.putExtra(DetailActivity.authorArticle, author)
            intentDetail.putExtra(DetailActivity.publishedArticle, date)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listArticle.size

    class ViewHolder(var binding: ItemCardProfileBinding) : RecyclerView.ViewHolder(binding.root)
}