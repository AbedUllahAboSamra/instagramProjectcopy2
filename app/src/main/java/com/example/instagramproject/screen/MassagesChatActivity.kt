package com.example.instagramproject.screen

import android.app.usage.UsageEvents
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.OrientationEventListener
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramproject.adapter.AdapterMassinger
import com.example.instagramproject.databinding.ActivityMassagesChatBinding
import com.example.instagramproject.fragment.ChatsFragment
import com.example.instagramproject.model.FollowingModel
import com.example.instagramproject.model.MassageModle
import com.example.instagramproject.model.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MassagesChatActivity : AppCompatActivity() {
    var id = ""
    lateinit var adapter: AdapterMassinger
    var massagesInChat = ArrayList<MassageModle>()
    lateinit var binding: ActivityMassagesChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        massagesInChat.clear()
        super.onCreate(savedInstanceState)
        binding = ActivityMassagesChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = intent.getStringExtra("id").toString()
        var name = intent.getStringExtra("name")
        var image = intent.getStringExtra("image")

        getMassages()

        adapter = AdapterMassinger(massagesInChat)
        binding.recChatMassages.layoutManager = LinearLayoutManager(this)
        binding.recChatMassages.adapter = adapter
//  binding.imgChasdatMassages

        binding.chatUserName.text = name
        Picasso.with(this).load(image).into(binding.userImageId)
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(id!!)
            .get()
            .addOnSuccessListener { it ->

                var following = ArrayList<FollowingModel>()
                var followers = ArrayList<FollowingModel>()
                var posts = ArrayList<String>()

//get get  following following
                FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(id)
                    .collection("following")
                    .get()
                    .addOnSuccessListener { ites ->

                        for (i in ites) {
                            var followingModel = FollowingModel(
                                userId = i.getString("userId").toString(),
                                followDate = i.getString("followDate").toString(),
                                isFollow = i.getBoolean("isFollow") == true
                            )
                            following.add(followingModel)
                        }
                    }


//followers followers followers followers
                FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(id)
                    .collection("followers")
                    .get()
                    .addOnSuccessListener { ites ->
                        for (i in ites) {
                            var followingModel = FollowingModel(
                                userId = i.getString("userId").toString(),
                                followDate = i.getString("followDate").toString(),
                                isFollow = i.getBoolean("isFollow") == true
                            )
                            if (followingModel.userId == SplachActivity.uId) {
                                //      doocumentCurrentUserFollowersId = i.id
                            }

                            followers.add(followingModel)
                        }

                    }

///posts posts posts
                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(id)
                    .collection("posts")
                    .get()
                    .addOnSuccessListener { postsss ->
                        for (i in postsss) {
                            posts.add(i.id)
                        }
                    }


                var user = UserModel(
                    uId = id,
                    userName = it.getString("userName").toString(),
                    imageUrl = it.getString("imageUrl").toString(),
                    accountName = it.getString("accountName").toString(),
                    email = it.getString("email").toString(),
                    password = it.getString("password").toString(),
                    followers = followers,
                    folloeing = following,
                    posts = posts,
                    pio = it.getString("pio").toString(),
                )
                binding.tvAccountName.text = user.accountName
            }.addOnFailureListener {
            }






        binding.btnSend.visibility = View.GONE

        binding.edtMassage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 0) {
                    binding.lastLLayout.visibility = View.GONE
                    binding.btnSend.visibility = View.VISIBLE

                } else {
                    binding.lastLLayout.visibility = View.VISIBLE
                    binding.btnSend.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })


        binding.btnSend.setOnClickListener {
            var text = binding.edtMassage.text.toString()
            if (text != "") {
                sendMassage(text)
                binding.edtMassage.setText("")
            }
        }
    }

    fun getMassages() {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(SplachActivity.uId)
            .collection("chats")
            .document(id)
            .collection("massages")
            .orderBy("senfdateTime")
            .addSnapshotListener { value, error ->
                massagesInChat.clear()
                if (value!!.size() > 0) {
                    Log.e("ASD", value.size().toString() + "ASDASD")
                    for (massage in value) {
                        var massageModle = MassageModle(
                            id = massage.id,
                            senderId = massage.getString("senderId")
                                .toString(),
                            recevierId = massage.getString("recevierId")
                                .toString(),
                            senfdateTime = massage.getString("senfdateTime")
                                .toString(),
                            text = massage.getString("text").toString(),
                            isSeen = massage.getBoolean("isSeen") == true,
                            imageType = massage.getString("imageType")
                                .toString(),
                            timeSeen = massage.getString("timeSeen")
                                .toString(),
                            isVoce = massage.getBoolean("isVoce"),
                            massageType = massage.getString("massageType")
                                .toString(),
                            voiceUrl = massage.getString("voiceUrl")
                                .toString(),
                            imageUrl = massage.getString("imageUrl")
                                .toString()
                        )

                        if (!massagesInChat.contains(massageModle)) {
                                    massagesInChat.add(massageModle)
                                    adapter.notifyData()
                        }


                    }
                }

            }


    }

    fun sendMassage(text: String) {
        var massageMap = HashMap<String, Any>()
        massageMap["senderId"] = SplachActivity.uId
        massageMap["recevierId"] = id
        massageMap["senfdateTime"] =
            SimpleDateFormat("yyyy.MM.dd 'at' h:mm a", Locale.getDefault()).format(
                Date()
            )
        massageMap["text"] = text
        massageMap["isSeen"] = false
        massageMap["imageUrl"] = ""
        massageMap["timeSeen"] = ""
        massageMap["voiceUrl"] = ""
        massageMap["massageType"] = "t"
        massageMap["isVoce"] = false

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(SplachActivity.uId)
            .collection("chats")
            .document(id)
            .set(HashMap<String, Any>())
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(SplachActivity.uId)
            .collection("chats")
            .document(id)
            .collection("massages")
            .add(massageMap)


        FirebaseFirestore.getInstance()
            .collection("users")
            .document(id)
            .collection("chats")
            .document(SplachActivity.uId)
            .set(HashMap<String, Any>())

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(id)
            .collection("chats")
            .document(SplachActivity.uId)
            .collection("massages")
            .add(massageMap)

    }

    override fun onStop() {
        Log.e("ASD", "OnStop")
        super.onStop()
    }
}
