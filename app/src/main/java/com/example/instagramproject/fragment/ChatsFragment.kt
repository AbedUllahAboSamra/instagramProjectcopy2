package com.example.instagramproject.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramproject.R
import com.example.instagramproject.adapter.AdapterChatsAdapter
import com.example.instagramproject.databinding.FragmentChatsBinding
import com.example.instagramproject.model.MassageModle
import com.example.instagramproject.model.UserModel
import com.example.instagramproject.screen.MainActivity
import com.example.instagramproject.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore

class ChatsFragment : Fragment() {

    companion object {
        lateinit var binding: FragmentChatsBinding
        var chatsOfPositions = ArrayList<ArrayList<MassageModle>?>()

    }

    var arrChatUsers = ArrayList<UserModel>()
    var adapter = AdapterChatsAdapter(arrChatUsers)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(layoutInflater)
        getChats()

        binding.recChats.adapter = adapter

        binding.recChats.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.userName.text = SplachActivity.currentUser?.userName ?: "ASD"

        return binding.root
    }

    fun getChats() {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(SplachActivity.uId)
            .collection("chats")
            .addSnapshotListener { chatsItems, error ->
                arrChatUsers.clear()
                for (i in chatsItems!!) {
                    Log.e("ASD", i.id.toString() + "ASDDSA")

                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(i.id)
                        .get()
                        .addOnSuccessListener { usr ->
                            var user = UserModel(
                                uId = i.id,
                                accountName = usr.getString("accountName")
                                    .toString(),
                                email = usr.getString("email").toString(),
                                password = usr.getString("password").toString(),
                                userName = usr.getString("userName").toString(),
                                imageUrl = usr.getString("imageUrl").toString(),
                                posts = null,
                                followers = null,
                                folloeing = null,
                                pio = usr.getString("pio").toString(),
                                notfication = null,
                                senderNotfication = null
                            )
                            Log.e("ASD", "Abod ayman " + user.userName)
                            if (!arrChatUsers.contains(user)) {
                                arrChatUsers.add(user)

                            }
                            adapter.nty()


                            var arr = ArrayList<MassageModle>()
//                            FirebaseFirestore.getInstance()
//                                .collection("users")
//                                .document(SplachActivity.uId)
//                                .collection("chats")
//                                .document(i.id)
//                                .collection("massages")
//                                .orderBy("senfdateTime")
//                                .get()
//                                .addOnSuccessListener { massages ->
//                                    Log.e("ASD", "ASD")
//                                    for (massage in massages) {
//                                        var massageModle = MassageModle(
//                                            id = massage.id,
//                                            senderId = massage.getString("senderId")
//                                                .toString(),
//                                            recevierId = massage.getString("recevierId")
//                                                .toString(),
//                                            senfdateTime = massage.getString("senfdateTime")
//                                                .toString(),
//                                            text = massage.getString("text").toString(),
//                                            isSeen = massage.getBoolean("isSeen") == true,
//                                            imageType = massage.getString("imageType")
//                                                .toString(),
//                                            timeSeen = massage.getString("timeSeen")
//                                                .toString(),
//                                            isVoce = massage.getBoolean("isVoce"),
//                                            massageType = massage.getString("massageType")
//                                                .toString(),
//                                            voiceUrl = massage.getString("voiceUrl")
//                                                .toString(),
//                                            imageUrl = massage.getString("imageUrl")
//                                                .toString()
//                                        )
//                                        Log.e("ASD", massageModle.text.toString())
//                                        arr.add(massageModle)
//                                    }
//                                    chatsOfPositions.add(arr)
//
//                                }.addOnFailureListener {
//                                    Log.e("ASD", it.toString() + "ASDDSA")
//                                }

                            adapter.notifyDataSetChanged()
                        }

                    Log.e("ASD", i.id)


                }
            }
    }
}