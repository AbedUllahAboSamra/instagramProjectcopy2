package com.example.instagramproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.R
import com.example.instagramproject.databinding.DesignStoryItemBinding
import com.example.instagramproject.model.StoryModle
import com.example.instagramproject.model.UserModel
import com.example.instagramproject.screen.SplachActivity
import com.squareup.picasso.Picasso

class AdapterRec_Story(var context: Context, var arr: ArrayList<UserModel>) :
    RecyclerView.Adapter<AdapterRec_Story.myViewHolder>() {

    class myViewHolder(var binding: DesignStoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val binding = DesignStoryItemBinding.inflate(LayoutInflater.from(context), null, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.binding.btnAddStory.visibility = View.GONE
        if (position == 0) {

            holder.binding.btnAddStory.visibility = View.VISIBLE
            Picasso.get().load(SplachActivity.currentUser!!.imageUrl)
                .into(holder.binding.userImageId)

        }else{
            Picasso.get().load(arr[position - 1].imageUrl)
                .into(holder.binding.userImageId)


        }



    }

    override fun getItemCount(): Int {
        return arr.size + 1
    }
}