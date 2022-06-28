package com.example.instagramproject.ScandryFragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.instagramproject.R
import com.example.instagramproject.adapter.Adapter_ReelRecycleView
import com.example.instagramproject.adapter.reelsViewPager
import com.example.instagramproject.databinding.FragmentRealsBinding
import com.example.instagramproject.model.PostModel
import com.example.instagramproject.screen.SplachActivity
import java.security.spec.ECField


class RealsFragment : Fragment() {
    lateinit var binding: FragmentRealsBinding

    companion object {
        var currentPage = 1

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRealsBinding.inflate(layoutInflater)

        //        binding.recReels.layoutManager =
        //            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        var reels =
            SplachActivity.postsArray.filter { it -> it.type == "r" } as ArrayList<PostModel>
        //        var adapter = Adapter_ReelRecycleView(reels)
        //         binding.recReels.adapter=adapter


        var adapter = reelsViewPager(reels, requireActivity())
        binding.recReels.adapter = adapter

        // reelsViewPager.mediaPlayer.start()

        binding.recReels.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {


            }

            override fun onPageSelected(position: Int) {

                try {
                    Handler().postDelayed({
                        currentPage = position
                        if (reelsViewPager.mediaPlayer.isPlaying) {
                            reelsViewPager.mediaPlayer.stop()
                        } else {
                            reelsViewPager.mediaPlayer.start()
                        }

                    }, 800)
                } catch (e: Exception) {
                }


            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        return binding.root
    }
}