package com.capstone.perigigiapps.ui.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.perigigiapps.R

class AccountSettingFragment : Fragment() {

    companion object {
        fun newInstance() = AccountSettingFragment()
    }

    private lateinit var viewModel: AccountSettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountSettingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}