package com.example.instagramproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.databinding.DesignMassagesForAdapterBinding
import com.example.instagramproject.model.MassageModle
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AdapterMassinger(var arr: ArrayList<MassageModle>) :
    RecyclerView.Adapter<AdapterMassinger.myViewHolder>() {

    class myViewHolder(var binding: DesignMassagesForAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var binding = DesignMassagesForAdapterBinding.inflate(LayoutInflater.from(parent.context))
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.binding.imgImagedender.visibility = View.VISIBLE
        holder.binding.imgImagerecive.visibility = View.VISIBLE
        holder.binding.textviewReciver.visibility = View.VISIBLE
        holder.binding.textviewSender.visibility = View.VISIBLE
        var mass = arr[position]



        if (mass.senderId != SplachActivity.uId && !mass.isSeen) {
            var map = HashMap<String, Any>()
            map["isSeen"] = true
            map["timeSeen"] =
                SimpleDateFormat(
                    "yyyy.MM.dd 'at' h:mm a",
                    Locale.getDefault()
                ).format(
                    Date()
                ).toString()

                        FirebaseFirestore . getInstance ()
                    .collection("users")
                    .document(SplachActivity.uId)
                    .collection("chats")
                    .document(mass.senderId)
                    .collection("massages")
                    .document(mass.id)
                    .update(map)


        }





        if (mass.massageType == "t") {
            holder.binding.imgImagedender.visibility = View.GONE
            holder.binding.imgImagerecive.visibility = View.GONE
            if (mass.senderId == SplachActivity.uId) {
                holder.binding.textviewReciver.visibility = View.GONE
                holder.binding.textviewSender.text = mass.text
            } else {
                holder.binding.textviewSender.visibility = View.GONE
                holder.binding.textviewReciver.text = mass.text
            }
        }


    }

    override fun getItemCount(): Int {
        return arr.size
    }

    fun notifyData() {
        Log.e("ASD", "ASDASDASD ${arr.size}")
        notifyDataSetChanged()
    }
}