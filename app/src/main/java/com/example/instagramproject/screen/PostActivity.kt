package com.example.instagramproject.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramproject.R
import com.example.instagramproject.ScandryFragments.ProfileFragment
import com.example.instagramproject.adapter.AdapterRec_Posts
import com.example.instagramproject.databinding.ActivityPostBinding
import com.example.instagramproject.model.PostModel

class PostActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var context = intent.getStringExtra("context")
        var position = intent.getIntExtra("position", 0)

        binding.recPosts.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (context!!.contains("MainActivity")) {

            var adapter = AdapterRec_Posts(this, ProfileFragment.posts)
            binding.recPosts.adapter = adapter
            binding.userName.text = SplachActivity.currentUser!!.userName

        } else {
            PersonProfileActivity.postsss
            var adapter = AdapterRec_Posts(this, PersonProfileActivity.postsss)
            binding.recPosts.adapter = adapter
            binding.userName.text = PersonProfileActivity.postsss[0].posterName
        }

        binding.recPosts.smoothScrollToPosition(position)

        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }


    }
}