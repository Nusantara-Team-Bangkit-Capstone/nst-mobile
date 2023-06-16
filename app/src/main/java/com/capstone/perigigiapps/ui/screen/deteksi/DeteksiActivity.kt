package com.capstone.perigigiapps.ui.screen.deteksi

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.capstone.perigigiapps.R
import com.capstone.perigigiapps.databinding.ActivityDeteksiBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DeteksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeteksiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeteksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sectionPagerAdapter = SectionPagerAdapter(activity = this@DeteksiActivity)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}