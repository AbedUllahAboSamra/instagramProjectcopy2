package com.example.instagramproject.screen

import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ClipDrawable.VERTICAL
import android.icu.lang.UCharacter.DecompositionType.VERTICAL
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramproject.R
import com.example.instagramproject.ScandryFragments.SubMainFragment.Companion.postsAdapter
import com.example.instagramproject.adapter.CommentPostAdapter
import com.example.instagramproject.databinding.ActivityCommentBinding
import com.example.instagramproject.model.CommentModel
import com.example.instagramproject.model.LikeModel
import com.example.instagramproject.model.PostModel
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class CommentActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommentBinding
    lateinit var postId: String
    lateinit var postItem: PostModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postId = intent.getStringExtra("postId").toString()

        // post initialization
        for (i in SplachActivity.postsArray) {
            if (i.postId == postId) {
                postItem = i
            }
        }


        binding.btnPostComment.setTextColor(R.color.black)
        binding.edtCommentContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 0) {
                    binding.btnPostComment.setTextColor(R.color.blue)
                } else {
                    binding.btnPostComment.setTextColor(R.color.black)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })




        checkItems_Visiblity()

        // 1
        val u1 = "U+2764"
        var e1 = getEmojiByUnicode(u1)
        binding.e1.text = e1
        binding.e1.setOnClickListener { i ->
            var text = "${binding.edtCommentContent.text.toString()}$e1"
            binding.edtCommentContent.setText(text)
        }
        // 2
        val u2 = "U+1F64C"
        var e2 = getEmojiByUnicode(u2)
        binding.e2.text = e2
        binding.e2.setOnClickListener { i ->
            var text = "${binding.edtCommentContent.text.toString()}$e2"
            binding.edtCommentContent.setText(text)
        }
        // 3
        val u3 = "U+1F525"
        var e3 = getEmojiByUnicode(u3)
        binding.e3.text = e3
        binding.e3.setOnClickListener { i ->
            var text = "${binding.edtCommentContent.text.toString()}$e3"
            binding.edtCommentContent.setText(text)
        }
        // 4
        val u4 = "U+1F44F"
        var e4 = getEmojiByUnicode(u4)
        binding.e4.text = e4
        binding.e4.setOnClickListener { i ->
            var text = "${binding.edtCommentContent.text.toString()}$e4"
            binding.edtCommentContent.setText(text)
        }
        // 5
        val u5 = "U+1F625"
        var e5 = getEmojiByUnicode(u5)
        binding.e5.text = e5
        binding.e5.setOnClickListener { i ->
            var text = "${binding.edtCommentContent.text.toString()}$e5"
            binding.edtCommentContent.setText(text)
        }
        // 6
        val u6 = "U+1F60D"
        var e6 = getEmojiByUnicode(u6)
        binding.e6.text = e6
        binding.e6.setOnClickListener { i ->
            var text = "${binding.edtCommentContent.text.toString()}$e6"
            binding.edtCommentContent.setText(text)
        }
        // 7
        val u7 = "U+1F62E"
        var e7 = getEmojiByUnicode(u7)
        binding.e7.text = e7
        binding.e7.setOnClickListener { i ->
            var text = "${binding.edtCommentContent.text.toString()}$e7"
            binding.edtCommentContent.setText(text)
        }

        // 8
        val u8 = "U+1F602"
        var e8 = getEmojiByUnicode(u8)
        binding.e8.text = e8
        binding.e8.setOnClickListener { i ->
            var text = "${binding.edtCommentContent.text.toString()}$e8"
            binding.edtCommentContent.setText(text)
        }


        binding.btnArrowBack.setOnClickListener {
            this.onBackPressed()
        }


    }


    override fun onStart() {

        binding.btnpost.setOnClickListener {
            var content = binding.edtCommentContent.text.toString()
            if (content != null) {

                var commentModel = CommentModel(
                    commentId = "",
                    commentDate = "",
                    commenterName = SplachActivity.currentUser!!.userName,
                    commentLikes = ArrayList<LikeModel>(),
                    commentContent = content,
                    commenterImageUrl = SplachActivity.currentUser!!.imageUrl!!
                )


                // binding.layoutIfNoCommentAndPostContent.visibility = View.GONE
                // binding.layoutIfNoCommentAndPostContent.visibility = View.VISIBLE


                var mapComment = HashMap<String, Any>()
                mapComment["commentDate"] = ""
                mapComment["commenterName"] = SplachActivity.currentUser!!.userName
                mapComment["commentContent"] = content
                mapComment["commenterImageUrl"] = SplachActivity.currentUser!!.imageUrl!!


                binding.edtCommentContent.setText("")

                FirebaseFirestore.getInstance()
                    .collection("posts")
                    .document(postId)
                    .collection("comments")
                    .add(commentModel)
                    .addOnFailureListener {
                    }.addOnSuccessListener {


                        commentModel.commentId = it.id
                        checkItems_Visiblity()
                        for (i in SplachActivity.postsArray) {
                            if (i.postId == postId) {
                                i.comments?.add(commentModel)
                            }
                        }
                    }


            }

        }


        super.onStart()
    }


    fun getEmojiByUnicode(unicode: String): String? {
        return String(Character.toChars(Integer.parseInt(unicode.substring(2), 16)))
    }

    fun checkItems_Visiblity() {
        var arrayComments = postItem.comments

        if (postItem.postText != "" && postItem.comments != null) {
            binding.layoutIfNoCommentAndPostContent.visibility = View.GONE
            binding.layoutPostContentandComments.visibility = View.VISIBLE

            binding.tvPosterName.text = postItem.posterName
            binding.tvPosteContent.text = postItem.postText.toString()
            Picasso.get().load(postItem.posterImageUrl).into(binding.imvMyPhoto)


            var adapter = CommentPostAdapter(postId, arrayComments!!)
            binding.RecComments.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.RecComments.adapter = adapter

        } else if (postItem.postText == "" && postItem.comments == null) {
            binding.layoutIfNoCommentAndPostContent.visibility = View.VISIBLE
            binding.layoutPostContentandComments.visibility = View.GONE

        } else if (postItem.postText == "" && postItem.comments != null) {
            binding.layoutIfNoCommentAndPostContent.visibility = View.GONE
            binding.layoutPostContentandComments.visibility = View.VISIBLE
            binding.ifNoPostContentAndCommentsNotEmpty.visibility = View.GONE

            var adapter = CommentPostAdapter(postId, arrayComments!!)
            binding.RecComments.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.RecComments.adapter = adapter
        }


    }




}