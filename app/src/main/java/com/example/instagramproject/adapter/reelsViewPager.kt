package com.example.instagramproject.adapter

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.instagramproject.R
import com.example.instagramproject.ScandryFragments.RealsFragment
import com.example.instagramproject.databinding.DesignReelItemBinding
import com.example.instagramproject.model.LikeModel
import com.example.instagramproject.model.PostModel
import com.example.instagramproject.screen.CommentActivity
import com.example.instagramproject.screen.MainActivity
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class reelsViewPager(var arr: ArrayList<PostModel>, var context: Activity) : PagerAdapter() {
    override fun getCount(): Int {
        return arr.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as View)
    }

    companion object {
        var mediaPlayer = MediaPlayer()

    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        var binding = DesignReelItemBinding.inflate(LayoutInflater.from(context), null, false)

        binding.progressBar.visibility = View.VISIBLE
        Picasso.get().load(arr[position].posterImageUrl).into(binding.posterImage)
        Picasso.get().load(arr[position].posterImageUrl)
            .into(binding.posterImageToSong)

        binding.ReelVedio.setVideoURI(Uri.parse(arr[position].postImagesUrl!![0]))
        binding.posterName.text = arr[position].posterName

        binding.ReelVedio.setOnPreparedListener { mp ->
            mediaPlayer = mp
            Log.e("ASD", "PREPARED")
            binding.progressBar.visibility = View.GONE

            if (position == 0) {
                mp.start()
            }
        }
        binding.ReelVedio.setOnCompletionListener { mo ->
            mo.start()
        }

        var isLike = false

        if (arr[position].comments!!.size > 0) {
            binding.tvNumberOfComments.text =
                "${arr[position].comments!!.size}"
        } else {
            binding.tvNumberOfComments.text = ""
        }

        if (arr[position].likes?.isNotEmpty() == true) {
            binding.tvNumberOfLikes.text =
                "${arr[position].likes!!.size}"
            for (i in arr[position].likes!!) {
                if (i.likerId == SplachActivity.uId) {
                    isLike = i.isLike
                    if (i.isLike) {
                        binding.btnLike.setImageResource(R.drawable.ic_baseline_favorite_24)
                    } else {
                        binding.btnLike.setImageResource(R.drawable.ic_outline_favorite_border_24)

                    }
                }
            }

        } else {
            binding.tvNumberOfLikes.text = ""
        }


        // btn set Like
        binding.btnLike.setOnClickListener {
            isLike = !isLike

            if (isLike) {
                binding.btnLike.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_outline_favorite_border_24)

            }


            var mapLike = HashMap<String, Any>()
            mapLike["likeDate"] = Date().date.toString()
            mapLike["likerId"] = SplachActivity.uId
            mapLike["isLike"] = isLike

            var likeModle = LikeModel(
                likeDate = Date().date.toString(),
                id = "",
                likerId = SplachActivity.uId,
                isLike = isLike,
                commentIdOrPostId = arr[position].postId
            )
            if (arr[position].likes?.isNotEmpty() == true) {

                for (i in arr[position].likes!!) {

                    if (i.likerId == SplachActivity.uId) {

                        FirebaseFirestore.getInstance()
                            .collection("posts")
                            .document(arr[position].postId)
                            .collection("likes")
                            .document(i.id)
                            .set(mapLike)
                            .addOnSuccessListener {
                                Log.e("ASD", "update like  ")
                            }.addOnFailureListener {

                            }


                    } else {

                        FirebaseFirestore.getInstance()
                            .collection("posts")
                            .document(arr[position].postId)
                            .collection("likes")
                            .add(mapLike)
                            .addOnSuccessListener { it ->
                                arr[position].likes!!.add(likeModle)

                            }.addOnFailureListener {

                            }

                    }
                }
            } else {
                arr[position].likes = ArrayList<LikeModel>()
                FirebaseFirestore
                    .getInstance()
                    .collection("posts")
                    .document(arr[position].postId)
                    .collection("likes")
                    .add(mapLike)
                    .addOnSuccessListener {
                        likeModle.id = it.id
                        arr[position].likes!!.add(likeModle)
                    }.addOnFailureListener {
                    }
            }


        }

        //btn comment set On click listener
        binding.btnComment.setOnClickListener { _ ->
            var i = Intent(context, CommentActivity::class.java)
            i.putExtra("postId", arr[position].postId)
            context.startActivity(i)
        }

        binding.posterNameMusic.text = "${arr[position].posterName} . Original Audio"



        container.addView(binding.root)
        return binding.root
    }
}