package com.capstone.perigigiapps.ui.screen.konsultasi

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
import com.capstone.perigigiapps.databinding.FragmentKonsultasiBinding
import com.capstone.perigigiapps.di.Injection
import com.capstone.perigigiapps.network.NetworkResult

class KonsultasiFragment : Fragment() {
    private var _binding: FragmentKonsultasiBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var konsultasiViewModel: KonsultasiViewModel
    private lateinit var listDoctorAdapter: ListDoctorAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKonsultasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val doctorRepository = Injection.provideDoctorRepository()
        val factory = KonsultasiViewModel.KonsultasiViewModelFactory(doctorRepository)
        konsultasiViewModel = ViewModelProvider(this, factory)[KonsultasiViewModel::class.java]
        listAllDoctor()
    }

    private fun listAllDoctor() {
        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "").orEmpty()
        val jwt = JWT(token)
        val id = jwt.getClaim("id").asInt()
        konsultasiViewModel.getAllDoctor(token = token)
            .observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is NetworkResult.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is NetworkResult.Success -> {
                            binding.progressBar.isVisible = false
                            val response = result.data.data
                            listDoctorAdapter = ListDoctorAdapter(response)
                            binding.rvDoctor.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.rvDoctor.adapter = listDoctorAdapter
                        }

                        is NetworkResult.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}