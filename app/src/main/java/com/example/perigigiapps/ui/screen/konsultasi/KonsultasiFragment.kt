package com.example.perigigiapps.ui.screen.konsultasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.perigigiapps.databinding.FragmentKonsultasiBinding

class KonsultasiFragment : Fragment() {
    private var _binding: FragmentKonsultasiBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val konsultasiViewModel =
            ViewModelProvider(this).get(KonsultasiViewModel::class.java)

        _binding = FragmentKonsultasiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textKonsultasi
        konsultasiViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}