package com.example.dailymemo.Auth

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchingVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SearchingIdFragment()
            1 -> SearchingPasswordFragment()
            2 -> SearchingIdSuccessFragment()
            3 -> SearchingIdFailedFragment()
            4 -> SearchingPasswordInputFragment()
            else -> SearchingPasswordResetFragment()
        }
    }


}