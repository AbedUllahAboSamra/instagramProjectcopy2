package com.example.instagramproject.screen

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagramproject.adapter.AdapterRec_profileGrid
import com.example.instagramproject.databinding.ActivityPersonProfileBinding
import com.example.instagramproject.model.FollowingModel
import com.example.instagramproject.model.PostModel
import com.example.instagramproject.model.UserModel
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class PersonProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityPersonProfileBinding
 companion object {
     var postsss = ArrayList<PostModel>()
 }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var isIamFollow = false
        var isheFollowMe = false
        var doocumentCurrentUserFollowersId = ""
        var doocumentCurrentUserFollowingId = ""

        var id = intent.getStringExtra("personId").toString()


        var image = intent.getStringExtra("image")
        var name = intent.getStringExtra("name")
        Picasso.with(this)
            .load(image)
            .into(binding.userImageId)

        binding.tvAccountName
            .text = name


        FirebaseFirestore.getInstance()
            .collection("users")
            .document(id)
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
                            if (followingModel.userId == SplachActivity.uId && followingModel.isFollow) {

                                isheFollowMe = true
                                if (!isIamFollow && isheFollowMe) {
                                    binding.tvFollow.text = "Follow back"
                                } else {
                                    binding.tvFollow.text = "Follow"
                                }
                            }
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
                                doocumentCurrentUserFollowersId = i.id
                            }
                            if (followingModel.userId == SplachActivity.uId && followingModel.isFollow) {

                                isIamFollow = true
                                if (isIamFollow) {
                                    binding.tvFollow.text = "UnFollow"
                                    binding.idToChangeColorOnClick.setCardBackgroundColor(Color.rgb(176, 176, 176))
                                } else {
                                    binding.tvFollow.text = "Follow"
                                }
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

                var followersNum = 0
                var folloeingNum = 0

                Handler().postDelayed({
                    if (user.posts == null) {
                        binding.tvNumberOfPosts.text = 0.toString()
                    } else {
                        binding.tvNumberOfPosts.text = user.posts?.size.toString()


                        for (i in user.posts!!) {
                            var postArr =
                                SplachActivity.postsArray.filter { io -> i == io.postId }
                            postsss.addAll(postArr)
                        }
                    }
                    var arr = postsss.filter { it ->
                        it.type == "p"
                    } as ArrayList<PostModel>

                    var adapter = AdapterRec_profileGrid(this,arr)
                    binding.recGridView.adapter = adapter

                    Log.e("ASD", postsss.toString())

                    if (user.followers == null) {
                        binding.tvNumberOfFollowers.text = 0.toString()
                    } else {

                        for (i in user.followers!!) {
                            if (i.isFollow) {
                                followersNum++
                            }
                        }
                        binding.tvNumberOfFollowers.text = followersNum.toString()

                    }

                    if (user.folloeing == null) {
                        binding.tvNumberOfFollowing.text = 0.toString()
                    } else {

                        for (i in user.folloeing!!) {
                            if (i.isFollow) {
                                folloeingNum++
                            }
                        }
                        binding.tvNumberOfFollowing.text = folloeingNum.toString()
                    }
                    binding.tvUserName.text = user.userName
                    if (user.pio.isEmpty()) {
                        binding.tvPio.visibility = View.GONE
                        binding.btnSeeTranslation.visibility = View.GONE
                    } else {
                        binding.tvPio.text = user.pio
                    }

                    binding.progressBar.visibility = View.GONE


                }, 2000)


            }.addOnFailureListener {

            }



        binding.recGridView.layoutManager = GridLayoutManager(this, 3)


        binding.tpProfielTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {

                    0 -> {
                        var arr = postsss.filter { it ->
                            it.type == "p"
                        } as ArrayList<PostModel>


                        var adapter = AdapterRec_profileGrid(this@PersonProfileActivity,arr)
                        binding.recGridView.adapter = adapter
                        adapter.notifyData()

                    }
                    1 -> {


                        var arr = postsss.filter { it ->
                            it.type == "r"
                        } as ArrayList<PostModel>


                        var adapter = AdapterRec_profileGrid(this@PersonProfileActivity , arr)
                        binding.recGridView.adapter = adapter
                        adapter.notifyData()

                    }
                    2 -> {

                        var arr = postsss.filter { it ->
                            it.type == "m"
                        } as ArrayList<PostModel>


                        var adapter = AdapterRec_profileGrid(this@PersonProfileActivity,arr)
                        binding.recGridView.adapter = adapter
                        adapter.notifyData()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }


        })

        binding.btnFollow.setOnClickListener {
            isIamFollow = !isIamFollow

            if (isIamFollow) {
                binding.tvFollow.text = "UnFollow"
                binding.idToChangeColorOnClick.setCardBackgroundColor(Color.rgb(176, 176, 176))
            } else {
                binding.tvFollow.text = "Follow"
            }


            if (doocumentCurrentUserFollowersId != "" || doocumentCurrentUserFollowingId != "") {
                var map = HashMap<String, Any>()
                map["userId"] = SplachActivity.uId
                map["followDate"] = ""
                map["isFollow"] = isIamFollow
                FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(id)
                    .collection("followers")
                    .document(doocumentCurrentUserFollowersId)
                    .set(map)
                    .addOnSuccessListener {
//me
                        var smap = HashMap<String, Any>()
                        smap["userId"] = id
                        smap["followDate"] = ""
                        smap["isFollow"] = isIamFollow
                        FirebaseFirestore
                            .getInstance()
                            .collection("users")
                            .document(SplachActivity.uId)
                            .collection("following")
                            .document(doocumentCurrentUserFollowingId)
                            .set(map)
                            .addOnSuccessListener {

                            }
                    }.addOnSuccessListener {

                    }

            } else {
                var map = HashMap<String, Any>()
                map["userId"] = SplachActivity.uId
                map["followDate"] = ""
                map["isFollow"] = isIamFollow
                FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(id)
                    .collection("followers")
                    .add(map)
                    .addOnSuccessListener { ist->
//me
                        doocumentCurrentUserFollowersId=ist.id
                        var smap = HashMap<String, Any>()
                        smap["userId"] = id
                        smap["followDate"] = ""
                        smap["isFollow"] = isIamFollow
                        FirebaseFirestore
                            .getInstance()
                            .collection("users")
                            .document(SplachActivity.uId)
                            .collection("following")
                            .add(map)
                            .addOnSuccessListener { it ->
                                doocumentCurrentUserFollowingId = it.id

                                Log.e("ASD","here"+doocumentCurrentUserFollowersId)
                                Log.e("ASD","me"+doocumentCurrentUserFollowingId)
                            }
                    }.addOnSuccessListener {

                    }


            }


        }
        binding.btnMassage.setOnClickListener {
          var intent = Intent(this,MassagesChatActivity::class.java)
          intent.putExtra("id",id)
          intent.putExtra("name",name)
          intent.putExtra("image",image)
startActivity(intent)

        }
    }


}

