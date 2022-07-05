package com.example.instagramproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.databinding.DesignNotficationItemBinding
import com.example.instagramproject.model.NotficationModle
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

class AdapterNoftication(var arr: ArrayList<NotficationModle>) :
    RecyclerView.Adapter<AdapterNoftication.viewHolder>() {
    class viewHolder(var binding: DesignNotficationItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var binding =
            DesignNotficationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Picasso.get().load(arr[position].personImage).into(holder.binding.userImageId)

        if (arr[position].postImage.contains("image")) {
            Picasso.get().load(arr[position].postImage).into(holder.binding.imagePost)
        }
        holder.binding.notfcationText.text = arr[position].type

        val format = DateTimeFormatter.ofPattern("yyyy.MM.dd 'at' h:mm a")


        if (position != arr.size - 1) {
            if (arr[position].day.substring(0, 10) == arr[position + 1].day.substring(0, 10)) {
                holder.binding.tvDay.visibility = View.GONE
            } else {
                if (SimpleDateFormat("yyyy.MM.dd 'at' h:mm a", Locale.getDefault()).format(
                        Date()
                    ).substring(0, 10) == arr[position].day.substring(0, 10)
                ) {
                    holder.binding.tvDay.visibility = View.VISIBLE
                    holder.binding.tvDay.text = "Today"
                } else {
                    var week = ChronoUnit.WEEKS.between(
                        LocalDateTime.parse(
                            SimpleDateFormat("yyyy.MM.dd 'at' h:mm a", Locale.getDefault()).format(
                                Date()
                            ), format
                        ),
                        LocalDateTime.parse(arr[position].day, format)
                    )
                    Log.e("ASD", week.toString() + " ASD WEEK ")
                    if (week.toInt() == 0) {
                        holder.binding.tvDay.visibility = View.VISIBLE
                        holder.binding.tvDay.text = "This week"
                    } else {
                        var month = ChronoUnit.MONTHS.between(
                            LocalDateTime.parse(
                                SimpleDateFormat(
                                    "yyyy.MM.dd 'at' h:mm a",
                                    Locale.getDefault()
                                ).format(
                                    Date()
                                ), format
                            ),
                            LocalDateTime.parse(arr[position].day, format)
                        )
                        if (month.toInt() == 0) {
                            holder.binding.tvDay.visibility = View.VISIBLE
                            holder.binding.tvDay.text = "This month"
                        } else {
                            holder.binding.tvDay.visibility = View.VISIBLE
                            holder.binding.tvDay.text = "This year"
                        }
                    }

                }


            }
        } else {
            holder.binding.tvDay.visibility = View.GONE

        }


    }

    override fun getItemCount(): Int {
        return arr.size
    }
}