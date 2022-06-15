package com.example.instagramproject.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.net.toUri
import androidx.viewpager.widget.PagerAdapter
import com.example.instagramproject.databinding.DesignImageOrVedioBinding

class adapterPagerFilesPick(var context: Context, var arr: ArrayList<Uri>) : PagerAdapter() {

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

        var binding =
            DesignImageOrVedioBinding.inflate(LayoutInflater.from(container.context), null, false)



        if (arr[position].toString().contains("video")) {
            binding.imageView.visibility = View.GONE
            binding.VideoView.visibility = View.VISIBLE

            binding.VideoView.setVideoURI(arr[position])
            binding.VideoView.setMediaController(MediaController(context))

        } else {
            binding.VideoView.visibility=View.GONE
            binding.imageView.visibility = View.VISIBLE

            binding.imageView.setImageURI(arr[position])
        }
        container.addView(binding.root)
        return binding.root

    }
    fun notitydataSet(){
        notifyDataSetChanged()
    }

}