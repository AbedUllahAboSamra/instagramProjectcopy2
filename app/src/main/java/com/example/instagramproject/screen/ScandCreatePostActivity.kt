package com.example.instagramproject.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagramproject.databinding.ActivityScandCreatePostBinding

class ScandCreatePostActivity : AppCompatActivity() {
    lateinit var binding: ActivityScandCreatePostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScandCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
    }
}