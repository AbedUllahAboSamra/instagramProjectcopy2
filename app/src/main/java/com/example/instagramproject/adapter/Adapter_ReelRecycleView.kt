package com.example.instagramproject.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.R
import com.example.instagramproject.databinding.DesignReelItemBinding
import com.example.instagramproject.model.LikeModel
import com.example.instagramproject.model.PostModel
import com.example.instagramproject.screen.CommentActivity
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Adapter_ReelRecycleView(var arr: ArrayList<PostModel>) :
    RecyclerView.Adapter<Adapter_ReelRecycleView.myViewHolder>() {

    class myViewHolder(var binding: DesignReelItemBinding) : RecyclerView.ViewHolder(binding.root)


    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding = DesignReelItemBinding.inflate(LayoutInflater.from(parent.context))
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        Picasso.with(context).load(arr[position].posterImageUrl).into(holder.binding.posterImage)
        holder.binding.ReelVedio.setVideoURI(Uri.parse(arr[position].postImagesUrl!![0]))
        holder.binding.ReelVedio.start()
        holder.binding.posterName.text = arr[position].posterName

        holder.binding.ReelVedio.setOnPreparedListener { mp ->
            mp.isLooping = true
        }

        var isLike = false

        if (arr[position].comments!!.size > 0) {
            holder.binding.tvNumberOfComments.text =
                "${arr[position].comments!!.size}"
        } else {
            holder.binding.tvNumberOfComments.visibility = View.GONE
        }

        if (arr[position].likes?.isNotEmpty() == true) {
            holder.binding.tvNumberOfLikes.text =
                "${arr[position].likes!!.size}"
            for (i in arr[position].likes!!) {
                if (i.likerId == SplachActivity.uId) {
                    isLike = i.isLike
                    if (i.isLike) {
                        holder.binding.btnLike.setImageResource(R.drawable.ic_baseline_favorite_24)
                    } else {
                        holder.binding.btnLike.setImageResource(R.drawable.ic_outline_favorite_border_24)

                    }
                }
            }

        } else {
            holder.binding.tvNumberOfLikes.visibility = View.GONE
        }


        // btn set Like
        holder.binding.btnLike.setOnClickListener {
            isLike = !isLike

            if (isLike) {
                holder.binding.btnLike.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                holder.binding.btnLike.setImageResource(R.drawable.ic_outline_favorite_border_24)

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
                    }.addOnFailureListener {}
            }


        }

        //btn comment set On click listener
        holder.binding.btnComment.setOnClickListener { _ ->
            var i = Intent(context, CommentActivity::class.java)
            i.putExtra("postId", arr[position].postId)
            context.startActivity(i)
        }

        holder.binding.posterNameMusic.text = "${arr[position].posterName} . Original Audio"


    }

    override fun getItemCount(): Int {
        return arr.size
    }


}