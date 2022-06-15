package com.example.instagramproject.model

import android.media.Image

data class PostModel(
    var postId: String,
    var posterId: String,
    var postText: String?,
    var postPossition: String,
    var likes: ArrayList<LikeModel>,
    var comments: ArrayList<CommentModel>,
    var type:String,
    var text :String,
    var postImagesUrl:ArrayList<String>?,
    var postImageUrl:String?,
    var postVedioUrl:String?,
    var postDate:String,
    var posterName : String,
    var posterImageUrl: String,
    var peopleIdTags:ArrayList<String>?


)