package com.example.instagramproject.ScandryFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagramproject.adapter.AdapterRec_profileGrid
import com.example.instagramproject.adapter.PagerAdapter_ForMainActivity
import com.example.instagramproject.databinding.FragmentProfileBinding
import com.example.instagramproject.model.PostModel
import com.example.instagramproject.screen.SplachActivity
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    companion object {
        var posts = ArrayList<PostModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentProfileBinding.inflate(layoutInflater)
        var user = SplachActivity.currentUser!!


        if (user.posts!!.size != null) {
            for (p in SplachActivity.postsArray) {
                for (i in user.posts!!) {
                    if (p.postId == i) {
                        posts.add(p)
                    }
                }
            }
        }


        var followersNum = 0
        var folloeingNum = 0

        if (user.posts == null) {
            binding.tvNumberOfPosts.text = 0.toString()
        } else {
            binding.tvNumberOfPosts.text = user.posts?.size.toString()
        }

        if (user.followers == null) {
            binding.tvNumberOfFollowers.text = 0.toString()
        } else {

            for (i in user.followers!!) {
                if (i.isFollow) {
                    followersNum++
                }
            }
            binding.tvNumberOfFollowers.text = followersNum.toString()
        }

        if (user.folloeing == null) {
            binding.tvNumberOfFollowing.text = 0.toString()
        } else {

            for (i in user.folloeing!!) {
                if (i.isFollow) {
                    folloeingNum++
                }
            }
            binding.tvNumberOfFollowing.text = folloeingNum.toString()
        }


        binding.tvAccountName.text = user.accountName
        binding.tvUserName.text = user.userName

        if (user.pio.isEmpty()) {
            binding.tvPio.visibility = View.GONE
        } else {
            binding.tvPio.text = user.pio
        }

        Picasso.with(requireContext()).load(user.imageUrl).into(binding.userImageId)



        var arr = posts.filter { it ->
            it.type == "p"
        } as ArrayList<PostModel>

        binding.recGridView.layoutManager = GridLayoutManager(requireContext(), 3)

        var adapter = AdapterRec_profileGrid(arr)
        binding.recGridView.adapter = adapter


        binding.tpProfielTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {

                    0 -> {
                        var arr = posts.filter { it ->
                            it.type == "p"
                        } as ArrayList<PostModel>


                        var adapter = AdapterRec_profileGrid(arr)
                        binding.recGridView.adapter = adapter
                        adapter.notifyData()

                    }
                    1 -> {


                        var arr = posts.filter { it ->
                            it.type == "r"
                        } as ArrayList<PostModel>


                        var adapter = AdapterRec_profileGrid(arr)
                        binding.recGridView.adapter = adapter
                        adapter.notifyData()

                    }
                    2 -> {

                        var arr = posts.filter { it ->
                            it.type == "m"
                        } as ArrayList<PostModel>


                        var adapter = AdapterRec_profileGrid(arr)
                        binding.recGridView.adapter = adapter
                            adapter.notifyData()


                    }


                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }


        })


            return binding.root
    }
}