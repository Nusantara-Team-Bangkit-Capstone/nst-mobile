package com.example.perigigiapps.ui.screen.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.perigigiapps.R
import com.example.perigigiapps.ui.screen.login.LoginFragment

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val loginTextView = view.findViewById<TextView>(R.id.login_click)
        loginTextView.setOnClickListener {
            // Perform navigation to the register fragment
            navigateToLoginFragment()
        }

        return view
    }


    private fun navigateToLoginFragment() {
        // Create a new instance of the register fragment
        val loginFragment = LoginFragment()

        // Get the FragmentManager and start a transaction
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the current fragment with the register fragment
        fragmentTransaction.replace(R.id.frame_container, loginFragment)
        fragmentTransaction.addToBackStack(null) // Optional: Add to back stack if desired

        // Commit the transaction
        fragmentTransaction.commit()
    }

}