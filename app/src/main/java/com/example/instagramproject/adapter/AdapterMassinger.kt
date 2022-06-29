package com.example.instagramproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.databinding.DesignMassagesForAdapterBinding
import com.example.instagramproject.model.MassageModle

class AdapterMassinger(var arr: ArrayList<MassageModle>) :
    RecyclerView.Adapter<AdapterMassinger.myViewHolder>() {

    class myViewHolder(var binding: DesignMassagesForAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var binding = DesignMassagesForAdapterBinding.inflate(LayoutInflater.from(parent.context))
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
  //      holder.binding.textMassage.text = arr[position].text
    }

    override fun getItemCount(): Int {
        return 52
    }

    fun notifyData() {
        Log.e("ASD", "ASDASDASD ${arr.size}")

     //   notifyDataSetChanged()
    }
}