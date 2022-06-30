package com.example.instagramproject.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.databinding.DesignProfileVedioItemBinding
import com.example.instagramproject.model.PostModel
import com.example.instagramproject.screen.PostActivity
import com.squareup.picasso.Picasso

class AdapterRec_profileGrid(var context: Context, var arr: ArrayList<PostModel>) :
    RecyclerView.Adapter<AdapterRec_profileGrid.myViewHolder>() {


    class myViewHolder(var binding: DesignProfileVedioItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var binding =
            DesignProfileVedioItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        holder.binding.postImageView.visibility = View.VISIBLE
        holder.binding.ReelVedio.visibility = View.VISIBLE
        holder.binding.PostVedio.visibility = View.VISIBLE


        if (arr[position].postImagesUrl!!.size > 1) {
            holder.binding.ifmyltyimage.visibility = View.VISIBLE
        } else {
            holder.binding.ifmyltyimage.visibility = View.GONE

        }

        if (arr[position].type == "p") {
            holder.binding.ReelVedio.visibility = View.GONE

            if (arr[position].postImagesUrl!![0].contains("video")) {
                holder.binding.postImageView.visibility = View.GONE
                holder.binding.PostVedio.setVideoURI(Uri.parse(arr[position].postImagesUrl!![0]))
            } else {
                holder.binding.PostVedio.visibility = View.GONE
                Picasso.get().load(Uri.parse(arr[position].postImagesUrl!![0]))
                    .into(holder.binding.postImageView)
            }

        } else {
            holder.binding.PostVedio.visibility = View.GONE
            holder.binding.postImageView.visibility = View.GONE
            holder.binding.ReelVedio.setVideoURI(Uri.parse(arr[position].postImagesUrl!![0]))
        }


        holder.binding.root.setOnClickListener {

            var intent = Intent(context, PostActivity::class.java)
            intent.putExtra("context", context.toString())
            intent.putExtra("position", position)
            context.startActivity(intent)


        }


    }

    override fun getItemCount(): Int {
        return arr.size
    }

    fun notifyData() {
    //    notifyDataSetChanged()
    }

}