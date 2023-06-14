package com.capstone.perigigiapps.ui.screen.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.perigigiapps.R
import com.capstone.perigigiapps.data.entity.User
import com.capstone.perigigiapps.databinding.FragmentRegisterBinding
import com.capstone.perigigiapps.di.Injection
import com.capstone.perigigiapps.network.NetworkResult
import com.capstone.perigigiapps.ui.screen.login.LoginFragment

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var registerViewModel: RegisterViewModel
    private var name = ""
    private var address = ""
    private var email = ""
    private var password = ""
    private var gender = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginTextView = view.findViewById<TextView>(R.id.login_click)
        loginTextView.setOnClickListener {
            // Perform navigation to the register fragment
            navigateToLoginFragment()
        }

        val genderOptions = listOf("Laki-Laki", "Perempuan", "Rahasia")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_gender_item, genderOptions)
        val autoCompleteTextViewGender =
            view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewGender)
        autoCompleteTextViewGender.setAdapter(adapter)

        val userRepository = Injection.provideUserRepository()
        val factory = RegisterViewModel.RegisterViewModelFactory(userRepository)
        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
        val registerNavigation = view.findViewById<Button>(R.id.register_button)
        registerNavigation.setOnClickListener {
            name = binding.nameEditTextRegister.text.toString().trim()
            address = binding.addressEditTextRegister.text.toString().trim()
            email = binding.emailEditTextRegister.text.toString().trim()
            password = binding.passwordEditTextRegister.text.toString().trim()
            gender = autoCompleteTextViewGender.text.toString()
            val user = User(
                nama = name,
                alamat = address,
                jenis_kelamin = gender,
                email = email,
                password = password
            )
            if (name.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(
                    email.trim()
                ).matches() || password.trim().length < 8
            ) {
                validation()
            } else {
                registerViewModel.register(user = user).observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result) {
                            is NetworkResult.Loading -> {
                                binding.progressBar.isVisible = true
                            }

                            is NetworkResult.Success -> {
                                binding.progressBar.isVisible = false
                                Toast.makeText(
                                    activity,
                                    "Registration successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navigateToLoginFragment()
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
        }
    }

    private fun validation() {
        if (email.isEmpty()) {
            binding.emailTextInputRegister.error = "Email required"
        }
        if (password.isEmpty()) {
            binding.passwordTextInputRegister.error = "Password required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() && !email.isEmpty()) {
            binding.emailTextInputRegister.error = "Invalid Email"
        } else if (password.trim().length < 8) {
            binding.passwordTextInputRegister.error = "Password minimum 8"
        }
        if (name.isEmpty()) {
            binding.nameTextInputRegister.error = "Name required"
        }
        if (address.isEmpty()) {
            binding.addressTextInputRegister.error = "Alamat required"
        }
        if (gender.isEmpty()) {
            binding.dropGender.error = "Jenis Kelamin required"
        }
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