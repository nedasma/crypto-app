package com.example.cryptoapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * An adapter class for the ViewPager. The main function of it is to leverage the functionality given
 * by the [FragmentStateAdapter], where fragment instances can be created based on the provided position
 * of the fragment itself. The caveat is that the [fragmentList] must contain fragments in intended
 * order, otherwise the fragments will swipe between each other in an undesired fashion.
 */
class FragmentSwipeAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList = mutableListOf<Fragment>()

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    /**
     * Adds a [fragment] to the [fragmentList].
     */
    fun addFragment(fragment: Fragment) = fragmentList.add(fragment)
}