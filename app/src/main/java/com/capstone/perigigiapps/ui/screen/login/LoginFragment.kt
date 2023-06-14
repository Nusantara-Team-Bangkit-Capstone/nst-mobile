package com.capstone.perigigiapps.ui.screen.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.perigigiapps.R
import com.capstone.perigigiapps.data.entity.User
import com.capstone.perigigiapps.databinding.FragmentLoginBinding
import com.capstone.perigigiapps.di.Injection
import com.capstone.perigigiapps.network.NetworkResult
import com.capstone.perigigiapps.ui.screen.home.HomeActivity
import com.capstone.perigigiapps.ui.screen.register.RegisterFragment

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        To Register Page
        val registerTextView = view.findViewById<TextView>(R.id.register_click)
        registerTextView.setOnClickListener {
            // Perform navigation to the register fragment
            navigateToRegisterFragment()
        }

//        Login To Home Page
        val userRepository = Injection.provideUserRepository()
        val factory = LoginViewModel.LoginViewModelFactory(userRepository)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        val loginNavigation = view.findViewById<Button>(R.id.login_button)
        loginNavigation.setOnClickListener {
            val email = binding?.emailEditText?.text.toString().trim()
            val password = binding?.passwordEditText?.text.toString().trim()
            val user = User(email = email, password = password)
            if (email.isEmpty()) {
                binding?.emailEditText?.error = "Email required"
            }
            if (password.isEmpty()) {
                binding?.passwordTextInput?.error = "Password required"
            }
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity, "Email atau Password Wajib diisi", Toast.LENGTH_SHORT)
                    .show()
            } else {
                loginViewModel.login(user = user).observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result) {
                            is NetworkResult.Loading -> {
                                binding?.progressBar?.isVisible = true
                            }

                            is NetworkResult.Success -> {
                                binding?.progressBar?.isVisible = false
                                val token = result.data.data?.token.orEmpty()
                                val sharedPreferences = activity?.getSharedPreferences(
                                    "MyPrefs",
                                    Context.MODE_PRIVATE
                                )
                                sharedPreferences?.edit()?.putString("token", token)?.apply()
                                val intent = Intent(activity, HomeActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)

                            }

                            is NetworkResult.Error -> {
                                binding?.progressBar?.isVisible = false
                                Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
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