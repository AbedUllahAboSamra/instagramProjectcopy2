package com.example.instagramproject.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.instagramproject.adapter.AdapterChatsAdapter
import com.example.instagramproject.databinding.ActivitySplachBinding
import com.example.instagramproject.fragment.ChatsFragment
import com.example.instagramproject.model.*
import com.google.firebase.firestore.FirebaseFirestore

class SplachActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplachBinding

    companion object {
        var uId = ""
        var postsArray = ArrayList<PostModel>()
        var currentUser: UserModel? = null


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplachBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uId = getSharedPreferences("My", MODE_PRIVATE).getString("uId", "").toString()
        if (!uId.isEmpty()) {
            getCurrentUser() }

        postsArray = getPosts()
        Handler().postDelayed({
            if (uId.isEmpty()) {

                Log.e("ASD", "ASDsadasd" + postsArray.size.toString())
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
                              }, 3000)

    }

    fun getPosts(): ArrayList<PostModel> {
        val arr = ArrayList<PostModel>()

// get All posts
        FirebaseFirestore.getInstance().collection("posts")
            .get()
            .addOnSuccessListener { items ->
                for (i in items) {

                    var commentsArray = ArrayList<CommentModel>()
                    var postLikesArray = ArrayList<LikeModel>()
                    var tagPeopleIds = ArrayList<String>()
                    var imagesArray = ArrayList<String>()

                    var commentLikesArray = ArrayList<LikeModel>()

// get all comments
                    FirebaseFirestore.getInstance().collection("posts")
                        .document(i.id)
                        .collection("comments")
                        .get()
                        .addOnSuccessListener { commentsItems ->
                            for (comment in commentsItems) {
// get comment Likes
                                FirebaseFirestore.getInstance()
                                    .collection("posts")
                                    .document(i.id)
                                    .collection("comments")
                                    .document(comment.id)
                                    .collection("likes")
                                    .get()
                                    .addOnSuccessListener { commentLikes ->
                                        for (like in commentLikes) {
                                            var commentLike = LikeModel(
                                                id = like.id,
                                                likerId = like.getString("likerId").toString(),
                                                likeDate = like.getString("likeDate").toString(),
                                                isLike = like.getBoolean("isLike")!!,
                                                comment.id
                                            )
                                            commentLikesArray.add(commentLike)
                                        }

                                        Log.e("ASD", "Comment Like Success")
                                    }.addOnFailureListener {
                                        Log.e("ASD", "Comment Like Fail00")
                                    }


                                var commentModel = CommentModel(
                                    commentId = comment.id,
                                    commentContent = comment.getString("commentContent").toString(),
                                    commentLikes = commentLikesArray,
                                    commenterName = comment.getString("commenterName").toString(),
                                    commentDate = comment.getString("commentDate").toString(),
                                    commenterImageUrl = comment.getString("commenterImageUrl")
                                        .toString(),
                                )
                                commentsArray.add((commentModel))
                            }
                            Log.e("ASD", "get Comment Success")

                        }.addOnFailureListener {
                            Log.e("ASD", "get Comment Fail")

                        }
//get all Likes


                    FirebaseFirestore.getInstance()
                        .collection("posts")
                        .document(i.id)
                        .collection("likes")
                        .get()
                        .addOnSuccessListener { likesItems ->
                            for (like in likesItems) {

                                var likeitem = LikeModel(
                                    id = like.id,
                                    likerId = like.getString("likerId").toString(),
                                    likeDate = like.getString("likeDate").toString(),
                                    isLike = like.getBoolean("isLike")!!,
                                    i.id
                                )
                                postLikesArray.add(likeitem)

                            }
                            Log.e("ASD", "get likes Success")

                        }.addOnFailureListener {
                            Log.e("ASD", "get likes Fail")

                        }


                    //get All people Tags
                    FirebaseFirestore.getInstance()
                        .collection("posts")
                        .document(i.id)
                        .collection("peopleIdTags")
                        .get()
                        .addOnSuccessListener { tagIds ->
                            for (stp in tagIds) {
                                for (s in 0 until stp.data.size) {
                                    tagPeopleIds.add(stp.get("$s").toString())
                                }
                            }

                            Log.e("ASD", "get peopleIdTags Success ")
                        }
                        .addOnFailureListener {
                            Log.e("ASD", "get peopleIdTags Failure ")
                        }

                    // get all images

                    FirebaseFirestore.getInstance()
                        .collection("posts")
                        .document(i.id)
                        .collection("postImagesUrl")
                        .get()
                        .addOnSuccessListener { tagIds ->
                            for (stp in tagIds) {
                                for (s in 0 until stp.data.size) {
                                    imagesArray.add(stp.get("$s").toString())
                                }
                            }

                            Log.e("ASD", "get postImagesUrl Success ")
                        }
                        .addOnFailureListener {
                            Log.e("ASD", "get postImagesUrl Failure ")
                        }


                    var postItem = PostModel(
                        postPossition = i.getString("postPossition").toString(),
                        postId = i.id,
                        posterName = i.getString("posterName").toString(),
                        postDate = i.getString("postDate").toString(),
                        postText = i.getString("postText").toString(),
                        posterId = i.getString("posterId").toString(),
                        postImagesUrl = imagesArray,
                        posterImageUrl = i.getString("posterImageUrl").toString(),
                        peopleIdTags = tagPeopleIds,
                        likes = postLikesArray,
                        comments = commentsArray,
                        type = i.getString("type").toString()
                    )
                    arr.add(postItem)
                }
                Log.e("ASD", "get all sucsess KISS")

            }
            .addOnFailureListener { _ ->
                Log.e("ASD", "get all post Failure")
            }

        return arr
    }

fun getCurrentUser (){
    FirebaseFirestore.getInstance().collection("users")
        .document(uId)
        .get().addOnSuccessListener { it ->

            var following = ArrayList<FollowingModel>()
            var followers = ArrayList<FollowingModel>()
            var posts = ArrayList<String>()



//get get  following following
            FirebaseFirestore.getInstance().collection("users")
                .document(uId)
                .collection("following")
                .get()
                .addOnSuccessListener { ites ->
                    Log.e("ASD", "followingusersaddOnSuccessListener")

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
            FirebaseFirestore.getInstance().collection("users")
                .document(uId)
                .collection("followers")
                .get()
                .addOnSuccessListener { ites ->
                    var position = 0

                    for (i in ites) {
                        var followingModel = FollowingModel(
                            userId = i.getString("userId").toString(),
                            followDate = i.getString("followDate").toString(),
                            isFollow = i.getBoolean("isFollow") == true
                        )


                        followers.add(followingModel)
                        if (position == ites.size() - 1) {


                        }
                        position++

                    }


                }

///posts posts posts
            FirebaseFirestore.getInstance().collection("users")
                .document(SplachActivity.uId)
                .collection("posts")
                .get()
                .addOnSuccessListener { postsss ->
                    Log.e("ASD", "userspostsaddOnSuccessListener")

                    for (i in postsss) {
                        posts.add(i.id)
                    }

                }



            currentUser = UserModel(
                uId = uId,
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
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
}


}
