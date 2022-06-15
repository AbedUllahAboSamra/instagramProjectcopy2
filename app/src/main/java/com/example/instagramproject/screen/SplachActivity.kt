package com.example.instagramproject.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.instagramproject.R
import com.example.instagramproject.databinding.ActivitySplachBinding
import com.example.instagramproject.model.CommentModel
import com.example.instagramproject.model.LikeModel
import com.example.instagramproject.model.PostModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Comment

class SplachActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplachBinding

    companion object {
        var uId = ""
        var postsArray = ArrayList<PostModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplachBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uId = getSharedPreferences("My", MODE_PRIVATE).getString("uId", "").toString()


            postsArray = getPosts()


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
                                    .collection("commentLikes")
                                    .get()
                                    .addOnSuccessListener { commentLikes ->
                                        for (like in commentLikes) {
                                            var commentLike = LikeModel(
                                                id = like.id,
                                                likerImageUrl = like.getString("likerImageUrl")
                                                    .toString(),
                                                likerId = like.getString("likerId").toString(),
                                                likeDate = like.getString("likeDate").toString(),
                                                likerName = like.getString("likerName").toString(),
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


                    FirebaseFirestore.getInstance().collection("posts")
                        .document(i.id)
                        .collection("likes")
                        .get()
                        .addOnSuccessListener { likesItems ->
                            for (like in likesItems) {

                                var likeitem = LikeModel(
                                    id = like.id,
                                    likerImageUrl = like.getString("likerImageUrl")
                                        .toString(),
                                    likerId = like.getString("likerId").toString(),
                                    likeDate = like.getString("likeDate").toString(),
                                    likerName = like.getString("likerName").toString(),
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
                        comments = commentsArray
                    )
                    arr.add(postItem)
                }

                Handler().postDelayed({
                    if (uId.isEmpty()) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }


                }, 3000)
                Log.e("ASD", "get all sucsess Failure")

            }
            .addOnFailureListener { _ ->
                Log.e("ASD", "get all post Failure")
            }

        return arr
    }
}