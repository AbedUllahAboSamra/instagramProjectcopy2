package com.example.instagramproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.R
import com.example.instagramproject.databinding.DesignStoryItemBinding
import com.example.instagramproject.model.StoryModle

class AdapterRec_Story(var context: Context, var arr: ArrayList<String>) :
    RecyclerView.Adapter<AdapterRec_Story.myViewHolder>() {

    class myViewHolder(var binding: DesignStoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val binding = DesignStoryItemBinding.inflate(LayoutInflater.from(context),null,false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return arr.size
    }


}