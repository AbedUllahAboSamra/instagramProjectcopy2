package com.example.instagramproject.ScandryFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramproject.R
import com.example.instagramproject.adapter.AdapterNoftication
import com.example.instagramproject.databinding.FragmentNotficationBinding
import com.example.instagramproject.model.NotficationModle
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore

class NotficationFragment : Fragment() {

    lateinit var binding:
            FragmentNotficationBinding

    companion object {
        var notficationsArrr = ArrayList<NotficationModle>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentNotficationBinding.inflate(layoutInflater)
        var adapter = AdapterNoftication(notficationsArrr)

        binding.recNotfications.layoutManager = LinearLayoutManager(requireContext())

        //notfications notfications notfications
        Log.e("ASD", SplachActivity.currentUser!!.uId.toString() + "0 . S A D ")
        FirebaseFirestore.getInstance().collection("users")
            .document(SplachActivity.uId)
            .collection("notification")
            .orderBy("day")
            .addSnapshotListener { value, error ->
                notficationsArrr.clear()
                for (i in value!!) {
                    var notficationModle = NotficationModle(
                        id = i.id,
                        type = i.getString("type").toString(),
                        postId = i.getString("postId").toString(),
                        personName = i.getString("personName").toString(),
                        personId = i.getString("personId").toString(),
                        day = i.getString("day").toString(),
                        personImage = i.getString("personImage").toString(),
                        postImage = i.getString("postImage").toString(),
                    )
                    if (!notficationsArrr.contains(notficationModle)) {
                        notficationsArrr.add(notficationModle)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        binding.recNotfications.adapter = adapter
        return binding.root
    }
}