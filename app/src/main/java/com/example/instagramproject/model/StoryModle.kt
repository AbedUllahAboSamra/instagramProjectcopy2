package com.example.instagramproject.model

data class StoryModle(
    var id: String,
    var seenIds: ArrayList<String>,
    var sendTime: String,
    var imageOrVedioUrl: String?,
    var senderID: String,
    var senderImageUrl: String,
    var senderImageName: String,
    var hiddenFrom: ArrayList<String>,
    var hilightName: String,
    var isDeleted:Boolean
)
