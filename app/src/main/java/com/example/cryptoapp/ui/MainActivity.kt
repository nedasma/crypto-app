package com.example.cryptoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.example.cryptoapp.ui.adapter.FragmentSwipeAdapter
import com.example.cryptoapp.ui.fragment.CryptoListFragment
import com.example.cryptoapp.ui.fragment.WatchlistFragment

/**
 * Starting point for an application - the viewpager is set here so that swiping between fragments
 * could be made possible. See more on [FragmentSwipeAdapter] for the swipe-able fragments functionality.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pagerAdapter = FragmentSwipeAdapter(supportFragmentManager, lifecycle)
        pagerAdapter.addFragment(CryptoListFragment.newInstance())
        pagerAdapter.addFragment(WatchlistFragment.newInstance())
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = pagerAdapter
    }
}