package com.example.instagramproject.ScandryFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramproject.R
import com.example.instagramproject.adapter.Adapter_ReelRecycleView
import com.example.instagramproject.databinding.FragmentRealsBinding
import com.example.instagramproject.model.PostModel
import com.example.instagramproject.screen.SplachActivity


class RealsFragment : Fragment() {
    lateinit var binding: FragmentRealsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRealsBinding.inflate(layoutInflater)

        binding.recReels.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        var reels =
            SplachActivity.postsArray.filter { it -> it.type == "r" } as ArrayList<PostModel>
        var adapter = Adapter_ReelRecycleView(reels)
         binding.recReels.adapter=adapter





        return binding.root
    }
}