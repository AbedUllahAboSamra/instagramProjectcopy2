package com.example.instagramproject.screen

import android.app.usage.UsageEvents
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.OrientationEventListener
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramproject.databinding.ActivityMassagesChatBinding
import com.example.instagramproject.fragment.ChatsFragment
import com.example.instagramproject.model.FollowingModel
import com.example.instagramproject.model.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class MassagesChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMassagesChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var position = intent.getIntExtra("position", -1)
        var id = intent.getStringExtra("id")
        var name = intent.getStringExtra("name")
        var image = intent.getStringExtra("image")



        if (position != -1) {
            var arrayMassages = ChatsFragment.chatsOfPositions[position]


        } else {
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

        }
        //  binding.imgChasdatMassages


    }


}
