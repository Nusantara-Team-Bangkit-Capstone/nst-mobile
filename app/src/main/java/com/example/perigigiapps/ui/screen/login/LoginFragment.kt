package com.example.perigigiapps.ui.screen.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.perigigiapps.R
import com.example.perigigiapps.ui.screen.register.RegisterFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val registerTextView = view.findViewById<TextView>(R.id.register_click)
        registerTextView.setOnClickListener {
            // Perform navigation to the register fragment
            navigateToRegisterFragment()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun navigateToRegisterFragment() {
        // Create a new instance of the register fragment
        val registerFragment = RegisterFragment()

        // Get the FragmentManager and start a transaction
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the current fragment with the register fragment
        fragmentTransaction.replace(R.id.frame_container, registerFragment)
        fragmentTransaction.addToBackStack(null) // Optional: Add to back stack if desired

        // Commit the transaction
        fragmentTransaction.commit()
    }
}