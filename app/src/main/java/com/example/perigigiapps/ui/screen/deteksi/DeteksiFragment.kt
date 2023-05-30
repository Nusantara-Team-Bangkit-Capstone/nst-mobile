package com.example.perigigiapps.ui.screen.deteksi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.perigigiapps.databinding.FragmentDeteksiBinding

class DeteksiFragment : Fragment() {

    private var _binding: FragmentDeteksiBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val deteksiViewModel =
            ViewModelProvider(this).get(DeteksiViewModel::class.java)

        _binding = FragmentDeteksiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDeteksi
        deteksiViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}