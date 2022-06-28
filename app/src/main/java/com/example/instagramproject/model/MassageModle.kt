package com.example.instagramproject.model

data class MassageModle(
    var id: String,
    var senderId: String,
    var recevierId: String,
    var senfdateTime: String,
    var text: String?,
    var isSeen: Boolean,
    var imageUrl: String?,
    var imageType: String?,
    var timeSeen: String?,
    var isVoce: Boolean?,
    var massageType: String,
    var voiceUrl: String?
)