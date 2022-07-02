package com.example.instagramproject.screen

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instagramproject.adapter.PagerAdapter_ForMainActivity
import com.example.instagramproject.databinding.ActivityMainBinding
import com.example.instagramproject.fragment.ChatsFragment
import com.example.instagramproject.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    // :: :: ::
    lateinit var binding: ActivityMainBinding

    // :: :: ::
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arr = ArrayList<Fragment>()
        arr.add(MainFragment())
        arr.add(ChatsFragment())

        val adapter = PagerAdapter_ForMainActivity(supportFragmentManager, arr)
        binding.mainViewPager.adapter = adapter
    }


}