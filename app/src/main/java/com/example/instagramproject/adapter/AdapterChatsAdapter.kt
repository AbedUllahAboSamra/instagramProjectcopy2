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

        Picasso.with(context).load(arr[position].imageUrl).into(holder.binding.userImageId)
        holder.binding.chatUserName.text = arr[position].userName
        holder.binding.seenOrLastMassage.text = ChatsFragment.chatsOfPositions[0][0].text.toString()

        holder.binding.root.setOnClickListener {
            var i = Intent(context, MassagesChatActivity::class.java)
            i.putExtra("position", position)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return arr.size
    }


}