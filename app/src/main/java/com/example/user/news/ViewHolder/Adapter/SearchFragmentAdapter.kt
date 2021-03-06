package com.example.user.news.viewHolder.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class SearchFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()


    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]

    }

    override fun getCount(): Int {
        return mFragmentList.size

    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]

    }


}