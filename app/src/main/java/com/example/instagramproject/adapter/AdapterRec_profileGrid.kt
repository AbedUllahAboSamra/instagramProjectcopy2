package com.example.instagramproject.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.databinding.DesignProfileVedioItemBinding
import com.example.instagramproject.model.PostModel
import com.squareup.picasso.Picasso

class AdapterRec_profileGrid(var arr: ArrayList<PostModel>) :
    RecyclerView.Adapter<AdapterRec_profileGrid.myViewHolder>() {
    lateinit var context: Context

    class myViewHolder(var binding: DesignProfileVedioItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding =
            DesignProfileVedioItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        holder.binding.postImageView.visibility = View.VISIBLE
        holder.binding.ReelVedio.visibility = View.VISIBLE
        holder.binding.PostVedio.visibility = View.VISIBLE


        if (arr[position].type == "p") {
            holder.binding.ReelVedio.visibility = View.GONE

            if (arr[position].postImagesUrl!![0].contains("video")) {
                holder.binding.postImageView.visibility = View.GONE
                holder.binding.PostVedio.setVideoURI(Uri.parse(arr[position].postImagesUrl!![0]))
            } else {
                holder.binding.PostVedio.visibility = View.GONE
                Picasso.with(context).load(Uri.parse(arr[position].postImagesUrl!![0]))
                    .into(holder.binding.postImageView)
            }

        }else{
            holder.binding.PostVedio.visibility = View.GONE
            holder.binding.postImageView.visibility = View.GONE
            holder.binding.ReelVedio.setVideoURI(Uri.parse(arr[position].postImagesUrl!![0]))
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    fun notifyData (){
        notifyDataSetChanged()
    }

}