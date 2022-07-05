package com.example.instagramproject.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.R
import com.example.instagramproject.databinding.DesignStoryItemBinding
import com.example.instagramproject.model.StoryModle
import com.example.instagramproject.model.UserModel
import com.example.instagramproject.model.userStoryModle
import com.example.instagramproject.screen.AddStoryActivity
import com.example.instagramproject.screen.ShowStoryActivity
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AdapterRec_Story(var context: Context, var arr: ArrayList<userStoryModle>) :
    RecyclerView.Adapter<AdapterRec_Story.myViewHolder>() {

    class myViewHolder(var binding: DesignStoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val binding = DesignStoryItemBinding.inflate(LayoutInflater.from(context), null, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.binding.btnAddStory.visibility = View.GONE
        holder.binding.frame.isSelected = false


        if (position == 0) {
            SplachActivity.storyes.forEach { s ->
                if (s.senderID == SplachActivity.uId) {
                    holder.binding.frame.isSelected = s.seenIds.contains(SplachActivity.uId)

                }
            }
        }
        val format = DateTimeFormatter.ofPattern("yyyy.MM.dd 'at' h:mm a")

        if (SplachActivity.storyes.size != 0) {

            if (position != 0) {
                SplachActivity.storyes.forEach { s ->
                    if (s.senderID == arr[position - 1].id) {
                        holder.binding.frame.isSelected = s.seenIds.contains(SplachActivity.uId)
                    }
                }
            }
        }

        if (position == 0) {
            holder.binding.btnAddStory.visibility = View.VISIBLE
            Picasso.get().load(SplachActivity.currentUser!!.imageUrl)
                .into(holder.binding.userImageId)
        } else {
            Picasso.get().load(arr[position - 1].imageUrl)
                .into(holder.binding.userImageId)
        }

        holder.binding.root.setOnClickListener {
            if (position == 0) {

                if (SplachActivity.storyes.size != 0) {
                    SplachActivity.storyes.forEach { s ->
                        Log.e("ASD", "s.sendTime${s.sendTime}")
                        var day = ChronoUnit.DAYS.between(
                            LocalDateTime.parse(s.sendTime, format),
                            LocalDateTime.parse(
                                SimpleDateFormat(
                                    "yyyy.MM.dd 'at' h:mm a",
                                    Locale.getDefault()
                                ).format(
                                    Date()
                                ), format
                            )
                        )
                        if (s.senderID == SplachActivity.uId) {

                            if (day <= 1) {
                                context.startActivity(
                                    Intent(
                                        context,
                                        ShowStoryActivity::class.java
                                    )
                                )
                            } else {
                                context.startActivity(Intent(context, AddStoryActivity::class.java))
                            }
                        } else {
                            context.startActivity(Intent(context, AddStoryActivity::class.java))
                        }
                    }
                } else {
                    context.startActivity(Intent(context, AddStoryActivity::class.java))
                }
            } else {
                context.startActivity(Intent(context, ShowStoryActivity::class.java))
            }

        }

    }

    override fun getItemCount(): Int {
        return arr.size + 1
    }
    fun  notfiy(){
    notifyDataSetChanged()
    }
    }