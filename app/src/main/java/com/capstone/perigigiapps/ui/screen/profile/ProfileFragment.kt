package com.capstone.perigigiapps.ui.screen.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.auth0.android.jwt.JWT
import com.capstone.perigigiapps.MainActivity
import com.capstone.perigigiapps.R
import com.capstone.perigigiapps.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.constraintSetting.setOnClickListener {
            setting()
        }
        binding.constraintLogout.setOnClickListener {
            logout()
        }
        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "").orEmpty()

        val jwt = JWT(token)
        val name = jwt.getClaim("name").asString()
        val email = jwt.getClaim("email").asString()
        val photo = jwt.getClaim("foto").asString()
        binding.itemNameUser.text = name
        binding.itemEmailUser.text = email

        if (photo.isNullOrEmpty()) {
            binding.imgItemPhoto.setImageResource(R.drawable.avatar_male)
        }
        else {
            Picasso.get().load(photo).into(binding.imgItemPhoto);
        }
    }

    private fun setting() {
        val accountSettingFragment = AccountSettingFragment()

        // Get the FragmentManager and start a transaction
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the current fragment with the account setting fragment
        fragmentTransaction.replace(R.id.frame_container, accountSettingFragment)
        fragmentTransaction.addToBackStack(null) // Optional: Add to back stack if desired

        // Commit the transaction
        fragmentTransaction.commit()
    }


    private fun logout() {
        val sharedPreferences = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.remove("token")?.apply()
        sharedPreferences?.edit()?.clear()?.apply()
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}