package com.example.instagramproject.adapter

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.instagramproject.ScandryFragments.SubMainFragment
import com.example.instagramproject.databinding.DesignShowStoryItemBinding
import com.example.instagramproject.databinding.ShowSotryItemBinding
import com.example.instagramproject.model.StoryModle
import com.example.instagramproject.model.userStoryModle
import com.example.instagramproject.screen.MainActivity
import com.example.instagramproject.screen.ShowStoryActivity
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

// show activity adaoter


class AdapterShowStoryAdapter(var arr: ArrayList<userStoryModle>, var context: Context) :
    PagerAdapter() {
    override fun getCount(): Int {
        return arr.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var binding = DesignShowStoryItemBinding.inflate(LayoutInflater.from(context))

        var numItem = 0

        Picasso.get().load(arr[position].imageUrl).into(binding.userImageId)

        binding.tvPosterName.text = arr[position].name

        var arrr = ArrayList<StoryModle>()
        for (i in SplachActivity.storyes) {
            if (i.senderID == arr[position].id) {
                arrr.add(i)
            }
        }

        var adapter = pagerPersonViewAdapter(context, arrr)
        binding.storysVirePager.adapter = adapter

        binding.btnMinsStory.setOnClickListener {
            if (numItem != 0) {
                binding.storysVirePager.currentItem = binding.storysVirePager.currentItem - 1
                numItem -= 1
            }
        }
        binding.btnMoreStory.setOnClickListener {
            if (numItem <= arrr.size) {
                binding.storysVirePager.currentItem = binding.storysVirePager.currentItem + 1
                numItem += 1
            }

        }

        object : CountDownTimer(8000, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                if (position != arr.size - 1) {
                    if (numItem != arrr.size - 1) {
                        binding.storysVirePager.currentItem =
                            binding.storysVirePager.currentItem + 1
                        numItem += 1
                    } else {
                        ShowStoryActivity.binding.myViewPager.currentItem += 1

                    }


                } else {
                    if (numItem != arrr.size - 1) {
                        binding.storysVirePager.currentItem =
                            binding.storysVirePager.currentItem + 1
                        numItem += 1
                    } else {
                        context.startActivity(Intent(context, MainActivity::class.java))

                    }
                }
            }
        }.start()


        container.addView(binding.root)
        return binding.root
    }

}