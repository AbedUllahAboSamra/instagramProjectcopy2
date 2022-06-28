package com.example.instagramproject.model

class UserModel(
    var uId: String,
    var accountName: String,
    var email: String,
    var password: String,
    var userName: String,
    var imageUrl: String?,
    var posts: ArrayList<String>?,
    var followers: ArrayList<FollowingModel>?,
    var folloeing: ArrayList<FollowingModel>?,
    var pio: String,


    )