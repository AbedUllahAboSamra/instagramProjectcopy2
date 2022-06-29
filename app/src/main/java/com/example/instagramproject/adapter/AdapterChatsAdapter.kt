package com.example.instagramproject.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.databinding.DesignChatItemBinding
import com.example.instagramproject.fragment.ChatsFragment
import com.example.instagramproject.model.UserModel
import com.example.instagramproject.screen.MassagesChatActivity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

class AdapterChatsAdapter(var arr: ArrayList<UserModel>) :
    RecyclerView.Adapter<AdapterChatsAdapter.myViewHolder>() {

    lateinit var context: Context

    class myViewHolder(var binding: DesignChatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding =
            DesignChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
//        var msg =
//            ChatsFragment.chatsOfPositions[position]?.get(ChatsFragment.chatsOfPositions[position]!!.size - 1)
        Picasso.with(context).load(arr[position].imageUrl).into(holder.binding.userImageId)
        holder.binding.chatUserName.text = arr[position].userName

        val format = DateTimeFormatter.ofPattern("yyyy.MM.dd 'at' h:mm a")

//        if (msg?.isSeen == true) {
//            holder.binding.seenOrLastMassage.text = "seen"
//
//            var week = ChronoUnit.WEEKS.between(
//                LocalDateTime.parse(
//                    SimpleDateFormat("yyyy.MM.dd 'at' h:mm a", Locale.getDefault()).format(
//                        Date()
//                    ), format
//                ),
//                LocalDateTime.parse(msg.timeSeen, format)
//            )
//            if (week.toInt() == 0) {
//
//                var day = ChronoUnit.DAYS.between(
//                    LocalDateTime.parse(
//                        SimpleDateFormat("yyyy.MM.dd 'at' h:mm a", Locale.getDefault()).format(
//                            Date()
//                        ), format
//                    ),
//                    LocalDateTime.parse(msg.timeSeen, format)
//                )
//                if (day.toInt() == 0) {
//
//                    var hours = ChronoUnit.HOURS.between(
//                        LocalDateTime.parse(
//                            SimpleDateFormat("yyyy.MM.dd 'at' h:mm a", Locale.getDefault()).format(
//                                Date()
//                            ), format
//                        ),
//                        LocalDateTime.parse(msg.timeSeen, format)
//                    )
//
//                    if (hours.toInt() == 0) {
//
//                        var minits = ChronoUnit.MINUTES.between(
//                            LocalDateTime.parse(
//                                SimpleDateFormat(
//                                    "yyyy.MM.dd 'at' h:mm a",
//                                    Locale.getDefault()
//                                ).format(
//                                    Date()
//                                ), format
//                            ),
//                            LocalDateTime.parse(msg.timeSeen, format)
//                        )
//
//                        if (minits < 2) {
//                            holder.binding.time.text = "just now"
//                        } else {
//                            holder.binding.time.text = minits.toString()+"m"
//
//                        }
//
//
//                    } else {
//                        holder.binding.time.text = "${hours}h"
//                    }
//
//
//                } else {
//                    holder.binding.time.text = "${day}d"
//                }
//
//
//            } else {
//                holder.binding.time.text = "${week}w"
//            }
//
//
//        }
//        else{
//            holder.binding.seenOrLastMassage.text = msg?.text.toString()
//
//            var week = ChronoUnit.WEEKS.between(
//                LocalDateTime.parse(msg!!.senfdateTime, format)
//                ,
//                LocalDateTime.parse(
//                    SimpleDateFormat("yyyy.MM.dd 'at' h:mm a", Locale.getDefault()).format(
//                        Date()
//                    ), format
//                ))
//            Log.e("ASD","week $week")
//
//            if (week.toInt() == 0) {
//
//                var day = ChronoUnit.DAYS.between(
//                    LocalDateTime.parse(msg.senfdateTime, format)
//                    ,
//                    LocalDateTime.parse(
//                        SimpleDateFormat("yyyy.MM.dd 'at' h:mm a", Locale.getDefault()).format(
//                            Date()
//                        ), format
//                    ))
//                Log.e("ASD","day $day")
//
//                if (day.toInt() == 0) {
//
//                    var hours = ChronoUnit.HOURS.between(
//                        LocalDateTime.parse(msg.senfdateTime, format)
//                        ,
//                        LocalDateTime.parse(
//
//                            SimpleDateFormat("yyyy.MM.dd 'at' h:mm a", Locale.getDefault()).format(
//                                Date()
//                            ), format
//                        ))
//                    Log.e("ASD","hours $hours")
//
//                    if (hours.toInt() == 0) {
//
//                        var minits = ChronoUnit.MINUTES.between(
//                            LocalDateTime.parse(msg.senfdateTime, format)
//,
//                                    LocalDateTime.parse(
//
//                                SimpleDateFormat(
//                                    "yyyy.MM.dd 'at' h:mm a",
//                                    Locale.getDefault()
//                                ).format(
//                                    Date()
//                                ), format
//                            )
//                        )
//                        Log.e("ASD","minits $minits")
//
//                        if (minits < 2) {
//                            holder.binding.time.text = "just now"
//                        } else {
//                            holder.binding.time.text = "${minits}m"
//
//                        }
//
//
//                    } else {
//                        holder.binding.time.text = "${hours}h"
//                    }
//
//
//                } else {
//                    holder.binding.time.text = "${day}d"
//                }
//
//
//            } else {
//                holder.binding.time.text = "${week}w"
//            }
//
//        }


        holder.binding.root.setOnClickListener {
            var i = Intent(context, MassagesChatActivity::class.java)
            i.putExtra("id", arr[position].uId)
            i.putExtra("image", arr[position].imageUrl)
            i.putExtra("name", arr[position].userName)
            context.startActivity(i)
        }


    }

    override fun getItemCount(): Int {
        return arr.size
    }

fun nty(){
    notifyDataSetChanged()
}
}