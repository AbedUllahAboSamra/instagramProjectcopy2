package com.example.instagramproject.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.instagramproject.R
import com.example.instagramproject.adapter.PagerAdapter_ForMainActivity
import com.example.instagramproject.databinding.ActivityMainBinding
import com.example.instagramproject.fragment.ChatsFragment
import com.example.instagramproject.fragment.MainFragment
import com.example.instagramproject.fragment.MinsCameraFragment

class MainActivity : AppCompatActivity() {

    // :: :: ::
    lateinit var binding: ActivityMainBinding

    // :: :: ::
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arr = ArrayList<Fragment>()
        arr.add(MinsCameraFragment())
        arr.add(MainFragment())
        arr.add(ChatsFragment())
        val adapter = PagerAdapter_ForMainActivity(supportFragmentManager, arr)
        binding.mainViewPager.adapter = adapter
    }



}