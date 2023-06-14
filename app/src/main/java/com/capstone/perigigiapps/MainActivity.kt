package com.capstone.perigigiapps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.perigigiapps.ui.screen.home.HomeActivity
import com.capstone.perigigiapps.ui.screen.login.LoginFragment
import com.example.perigigiapps.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Using Flexible Fragment for Login and Register
        val fragmentManager = supportFragmentManager
        val loginFragment = LoginFragment()
        val fragment = fragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)
        if (fragment !is LoginFragment) {
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, loginFragment, LoginFragment::class.java.simpleName)
                .commit()
        }
//        To Stay Login
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        if (token != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}