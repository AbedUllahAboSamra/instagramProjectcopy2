package com.example.instagramproject.adapter

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.R
import com.example.instagramproject.databinding.DesignPostItemBinding
import com.example.instagramproject.model.LikeModel
import com.example.instagramproject.model.PostModel
import com.example.instagramproject.screen.CommentActivity
import com.example.instagramproject.screen.PersonProfileActivity
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AdapterRec_Posts(var context: Context, var arr: ArrayList<PostModel>) :
    RecyclerView.Adapter<AdapterRec_Posts.myViewHoleder>() {
   lateinit var adapter : adapterPagerFilesPick
    class myViewHoleder(var binding: DesignPostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHoleder {

        val binding =
            DesignPostItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
         adapter = adapterPagerFilesPick(context, ArrayList(), null)

        binding.pagerImages.adapter = adapter

        return myViewHoleder(binding)
    }


    override fun onBindViewHolder(holder: myViewHoleder, position: Int) {
        adapter.noty()

        Picasso.get().load(arr[position].posterImageUrl).into(holder.binding.userImageId)
        adapter = adapterPagerFilesPick(context,arr[position].postImagesUrl, null)
        adapter.noty()
        holder.binding.pagerImages.adapter = adapter

        holder.binding.tabTablayoutWithViewPager.setupWithViewPager(holder.binding.pagerImages)

        var post = arr[position]
        var isLike = false

        if (arr[position].comments!!.size > 0) {
            holder.binding.tvViewNumOfComment.text =
                "View all ${arr[position].comments!!.size} comments..."
        } else {
            holder.binding.tvViewNumOfComment.visibility = View.GONE
        }

        if (post.likes?.isNotEmpty() == true) {
            for (i in post.likes!!) {
                if (i.likerId == SplachActivity.uId) {
                    isLike = i.isLike
                    if (i.isLike) {
                        holder.binding.btnLike.setImageResource(R.drawable.ic_baseline_favorite_24)
                    } else {
                        holder.binding.btnLike.setImageResource(R.drawable.ic_outline_favorite_border_24)

                    }
                }
            }

        }

        holder.binding.tabTablayoutWithViewPager.visibility = View.VISIBLE
        holder.binding.pagerImages.clipToPadding = false
        holder.binding.pagerImages.pageMargin = 10



        if (post.postImagesUrl!!.size == 1) {
            holder.binding.tabTablayoutWithViewPager.visibility = View.GONE


        } else {
            holder.binding.tabTablayoutWithViewPager.visibility = View.VISIBLE
        }


        // initlzation post Item
        holder.binding.tvUserName.text = post.posterName
        holder.binding.tvLocation.text = post.postPossition
        holder.binding.tvTimePostAgo.text = post.postDate
        holder.binding.tvPosterName.text = post.posterName

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
            if (post.likes?.isNotEmpty() == true) {

                for (i in post.likes!!) {

                    if (i.likerId == SplachActivity.uId) {

                        FirebaseFirestore.getInstance()
                            .collection("posts")
                            .document(post.postId)
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
                            .document(post.postId)
                            .collection("likes")
                            .add(mapLike)
                            .addOnSuccessListener { it ->
                                post.likes!!.add(likeModle)

                            }.addOnFailureListener {

                            }

                    }
                }
            } else {
                post.likes = ArrayList<LikeModel>()
                post.likes!!.add(likeModle)
                FirebaseFirestore
                    .getInstance()
                    .collection("posts")
                    .document(post.postId)
                    .collection("likes")
                    .add(mapLike)
                    .addOnSuccessListener {
                        likeModle.id = it.id
                        post.likes!!.add(likeModle)
                        Log.e("ASD", it.id)
                        Log.e("ASD", "Create like")
                    }.addOnFailureListener {}
            }


        }

        //btn comment set On click listener
        holder.binding.btnComment.setOnClickListener { _ ->
            var i = Intent(context, CommentActivity::class.java)
            i.putExtra("postId", post.postId)
            context.startActivity(i)
        }


        //text align


        var space = ""
        for (i in 0..(arr[position].posterName.length)) {
            space += "   "
        }
        holder.binding.tvPostText.text = space + post.postText





        holder.binding.btnClickToOpenPersonProfile.setOnClickListener {
            Log
                .e("ASD", "Clicked")
            if (post.posterId == SplachActivity.uId) {
                Log.e("ASD", "Clickedtrue")
            } else {
                Log.e("ASD", "Clickedfalse")
                var i = Intent(context, PersonProfileActivity::class.java)
                i.putExtra("personId", post.posterId)
                i.putExtra("image", post.posterImageUrl)
                i.putExtra("name", post.posterName)
                context.startActivity(i)
            }

        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }


}