package com.capstone.perigigiapps.ui.screen.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.perigigiapps.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val nameArticles = intent.getStringExtra(
            nameArticle
        ).orEmpty()
        val publishedArticles = intent.getStringExtra(
            publishedArticle
        ).orEmpty()
        val authorArticles = intent.getStringExtra(
            authorArticle
        ).orEmpty()
        val descriptionArticles = intent.getStringExtra(
            descriptionArticle
        ).orEmpty()
        val photoArticles = intent.getStringExtra(
            photoArticle
        ).orEmpty()
        binding.apply {
            tvDetailName.text = nameArticles
            tvDetailDescription.text = descriptionArticles
            author.text = authorArticles
            tvDetailPublishedat.text = publishedArticles
        }
        Glide.with(this)
            .load(photoArticles)
            .into(binding.ivDetailPhoto)
    }

    companion object {
        val idArticle = "idArticle"
        val nameArticle = "nameArticle"
        val publishedArticle = "publishedArticle"
        val authorArticle = "authorArticle"
        val descriptionArticle = "descriptionArticle"
        val photoArticle = "photoArticle"
    }
}