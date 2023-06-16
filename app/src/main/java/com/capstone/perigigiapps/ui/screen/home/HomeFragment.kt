package com.capstone.perigigiapps.ui.screen.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.auth0.android.jwt.JWT
import com.capstone.perigigiapps.databinding.FragmentHomeBinding
import com.capstone.perigigiapps.di.Injection
import com.capstone.perigigiapps.network.NetworkResult

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var articleAdapter: BigArticleAdapter
    private lateinit var anotherArticleAdapter: SmallArticleAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val apiStoryRepository = Injection.provideArticleRepository()
        val factory = HomeViewModel.HomeViewModelFactory(apiStoryRepository)
        val homeViewModel =
            ViewModelProvider(this, factory)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "").orEmpty()

        val jwt = JWT(token)
        val name = jwt.getClaim("name").asString()
        binding.tvNameUser.text = name

        homeViewModel.getAllArticles().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is NetworkResult.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is NetworkResult.Success -> {
                        binding.progressBar.isVisible = false
                        val response = result.data.articles
                        articleAdapter = response?.let { BigArticleAdapter(it) }!!
                        binding.rvStory.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.rvStory.adapter = articleAdapter
                    }

                    is NetworkResult.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        homeViewModel.getAnotherArticles().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is NetworkResult.Loading -> {

                    }

                    is NetworkResult.Success -> {
                        val response = result.data.articles
                        anotherArticleAdapter = response?.let { SmallArticleAdapter(it) }!!
                        binding.rvAnother.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.rvAnother.adapter = anotherArticleAdapter
                    }

                    is NetworkResult.Error -> {

                    }
                }
            }
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val idArticle = ""
        val nameArticle = ""
        val publishedArticle = ""
        val authorArticle = ""
        val descriptionArticle = ""
        val photoArticle = ""
    }
}