package com.example.moodtrack.igethappy.adapters

import android.annotation.SuppressLint
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter

@SuppressLint("WrongConstant")
class CustomFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {


    var fragmentList: MutableList<Fragment> = mutableListOf()
    var fragmentTitleList: MutableList<String> = mutableListOf()

    fun add(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }


    fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList.get(position)
    }

    fun removeItem(currentItem: Int) {
        this.fragmentList.removeAt(currentItem)
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList.get(position)
    }


}
