package com.example.instagramproject.model

data class CommentModel(
    var commentId: String,
    var commentContent: String,
    var commentDate: String,
    var commentLikes: ArrayList<LikeModel>,
    var commenterImageUrl:String,
    var commenterName:String,


)
