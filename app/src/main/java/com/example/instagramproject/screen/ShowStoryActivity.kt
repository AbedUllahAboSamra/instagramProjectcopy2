package com.example.instagramproject.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramproject.ScandryFragments.SubMainFragment
import com.example.instagramproject.adapter.AdapterShowStoryAdapter
import com.example.instagramproject.databinding.ActivityShowStoryBinding

class ShowStoryActivity : AppCompatActivity() {
    companion object {
        lateinit var binding: ActivityShowStoryBinding

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var adapter = AdapterShowStoryAdapter(SubMainFragment.users, this)
        binding.myViewPager.adapter = adapter


    }
}