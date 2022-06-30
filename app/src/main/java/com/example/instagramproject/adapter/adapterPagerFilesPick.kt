package com.example.instagramproject.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.MediaController
import androidx.core.app.NotificationCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.instagramproject.R
import com.example.instagramproject.databinding.DesignImageOrVedioBinding
import com.example.instagramproject.databinding.DesignMassagesForAdapterBinding
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList


class adapterPagerFilesPick(var contex : Context, var arrayFromIInternt: ArrayList<String>?, var arr: ArrayList<Uri>?) :
    PagerAdapter() {
    fun noty() {
        notifyDataSetChanged()
    }
init {
    notifyDataSetChanged()

}
    override fun getCount(): Int {
        if (arr != null) {
            return arr!!.size
        } else {
            return arrayFromIInternt!!.size
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var binding =
            DesignImageOrVedioBinding.inflate(LayoutInflater.from(contex), null, false)
        if (arr != null) {
            if (arr!![position].toString().contains("video")) {
                val mediaController = MediaController(container.context)

                //      mediaController.setAnchorView(binding.VideoView)

                binding.imageView.visibility = View.GONE
                binding.LayoutFrameVideoView.visibility = View.VISIBLE
                binding.VideoView.setVideoURI(arr!![position])
                binding.VideoView.requestFocus()
//                binding.VideoView.setMediaController(mediaController)
            } else {
                binding.LayoutFrameVideoView.visibility = View.GONE
                binding.imageView.visibility = View.VISIBLE
                binding.imageView.setImageURI(arr!![position])
            }
        } else {
            if (arrayFromIInternt!![position].contains("video")) {


                binding.LayoutFrameVideoView.visibility = View.VISIBLE
                binding.imageView.visibility = View.GONE
                binding.VideoView.setVideoURI(Uri.parse(arrayFromIInternt!![position]))

                var isMute = true

                binding.VideoView.setOnPreparedListener { mp ->
                    binding.VideoView.start()
//                    amp = mp
                    mp.setVolume(0f, 0f)

                    binding.imgVolume.setImageResource(R.drawable.ic_baseline_volume_off_24)
                    mp.isLooping = true
                    binding.VideoView.setOnClickListener {
                        if (mp.isPlaying) {
                            mp.pause()
                        } else {
                            mp.start()
                        }
                    }
                    binding.imgVolume.setOnClickListener {

                        if (isMute) {

                            mp.setVolume(1f, 1f)
                            if (!mp.isPlaying) {
                                mp.start()
                            }
                            binding.imgVolume.setImageResource(R.drawable.ic_baseline_volume_up_24)
                        } else {
                            mp.setVolume(0f, 0f)
                            binding.imgVolume.setImageResource(R.drawable.ic_baseline_volume_off_24)

                        }
                        isMute = !isMute
                    }


                }

            } else {
                // internet
                Log.e("ASD","ASDASDimage")
                binding.LayoutFrameVideoView.visibility = View.GONE
                binding.imageView.visibility = View.VISIBLE

                Picasso
                    .get()
                    .load(arrayFromIInternt!![position])
                    .into(binding.imageView)
            }
        }


        container.addView(binding.root)
        return binding.root

    }


}