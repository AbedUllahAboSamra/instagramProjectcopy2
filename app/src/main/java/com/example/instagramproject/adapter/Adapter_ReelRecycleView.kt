package com.example.instagramproject.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.databinding.DesignReelItemBinding
import com.example.instagramproject.model.PostModel
import com.squareup.picasso.Picasso

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

        holder.binding.btnLike.setOnClickListener {








        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }


}