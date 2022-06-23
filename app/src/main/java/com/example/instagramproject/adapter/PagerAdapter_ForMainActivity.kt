package com.example.instagramproject.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter_ForMainActivity(var manager: FragmentManager , var arr : ArrayList<Fragment>):FragmentPagerAdapter(manager) {
    override fun getCount(): Int {
       return arr.size
    }

    override fun getItem(position: Int): Fragment {
       return arr[position]
    }

    fun notifyData(){
        notifyDataSetChanged()
    }
}