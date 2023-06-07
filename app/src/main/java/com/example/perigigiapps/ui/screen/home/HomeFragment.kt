package com.example.perigigiapps.ui.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perigigiapps.databinding.FragmentHomeBinding
import com.example.perigigiapps.di.Injection
import com.example.perigigiapps.network.NetworkResult

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var articleAdapter: BigArticleAdapter
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
//        (activity as AppCompatActivity).supportActionBar?.hide()
//        val textView: TextView = binding.textHome
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
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}