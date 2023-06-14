package com.capstone.perigigiapps.ui.screen.deteksi

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = SectionFragment()
        fragment.arguments = Bundle().apply {
            putInt(SectionFragment.ARG_POSITION, position + 1)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}