package com.example.instagramproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.R
import com.example.instagramproject.databinding.ActivityCommentBinding
import com.example.instagramproject.databinding.DesignCommentItemBinding
import com.example.instagramproject.model.CommentModel
import com.example.instagramproject.model.LikeModel
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlin.math.log

class CommentPostAdapter(var postId: String, var arr: ArrayList<CommentModel>) :
    RecyclerView.Adapter<CommentPostAdapter.myViewHolder>() {
    class myViewHolder(var binding: DesignCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding =
            DesignCommentItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return myViewHolder(binding)

    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        var IsMyLike = false





        Picasso.get().load(arr[position].commenterImageUrl)
            .into(holder.binding.imvCommenter)
        holder.binding.tvCommentContent.text = arr[position].commentContent
        holder.binding.tvCommenterName.text = arr[position].commenterName


        var mlikeModel = LikeModel("", "", "", false, null)

        for (i in arr[position].commentLikes) {
            if (i.likerId == SplachActivity.uId && i.commentIdOrPostId == arr[position].commentId) {
                mlikeModel = i
            } else {
                IsMyLike = false
            }
        }




        holder.binding.btnLikeComment.setOnClickListener {
            var slikeModel = mlikeModel

            for (i in arr[position].commentLikes) {
                if (i.likerId == SplachActivity.uId && i.commentIdOrPostId == arr[position].commentId) {
                    slikeModel = i
                    IsMyLike = i.isLike
                }

            }
            IsMyLike = !IsMyLike
            mlikeModel.isLike = IsMyLike

            var numberOfCommentsLike = 0
            Log.e("ASD","${arr[position].commentLikes.size}")
            for (i in arr[position].commentLikes) {
                if (i.isLike) {
Log.e("ASD","${i.isLike}")
                    numberOfCommentsLike++
                } else {
                    numberOfCommentsLike--
                }
            }

            if (numberOfCommentsLike > 0) {

                holder.binding.tvNumberOfLikes.text =
                    "$numberOfCommentsLike Like"

            } else {
                holder.binding.tvNumberOfLikes.visibility = View.GONE
            }



            if (IsMyLike) {
                holder.binding.btnLikeComment.setImageResource(R.drawable.ic_baseline_favorite_24)
                numberOfCommentsLike++
            } else {
                holder.binding.btnLikeComment.setImageResource(R.drawable.ic_outline_favorite_border_24)
                numberOfCommentsLike++
            }


            // notifyDataSetChanged
            var map = HashMap<String, Any>()
            map["likerId"] = SplachActivity.uId
            map["likeDate"] = ""
            map["isLike"] = IsMyLike





            if (slikeModel.likerId == SplachActivity.uId) {
                FirebaseFirestore
                    .getInstance()
                    .collection("posts")
                    .document(postId)
                    .collection("comments")
                    .document(arr[position].commentId)
                    .collection("likes")
                    .document(slikeModel.id)
                    .set(map)
                    .addOnSuccessListener {
                        slikeModel.isLike = IsMyLike
                    }
                    .addOnFailureListener {
                    }

            }
            else {
                FirebaseFirestore
                    .getInstance()
                    .collection("posts")
                    .document(postId)
                    .collection("comments")
                    .document(arr[position].commentId)
                    .collection("likes")
                    .add(map)
                    .addOnSuccessListener {

                        slikeModel.id = it.id
                        slikeModel.likerId = SplachActivity.uId
                        slikeModel.likeDate = ""
                        slikeModel.isLike = IsMyLike

                        arr[position].commentLikes.add(slikeModel)

                    }.addOnFailureListener {

                    }

            }
        }

        IsMyLike=mlikeModel.isLike
        if (IsMyLike) {
            holder.binding.btnLikeComment.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            holder.binding.btnLikeComment.setImageResource(R.drawable.ic_outline_favorite_border_24)
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }

}