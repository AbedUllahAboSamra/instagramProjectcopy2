package com.example.instagramproject.model

import android.os.Parcel
import android.os.Parcelable

class PostModel(
    var postId: String,
    var posterId: String,
    var postText: String?,
    var postPossition: String,
    var likes: ArrayList<LikeModel>?,
    var comments: ArrayList<CommentModel>?,
    var postImagesUrl: ArrayList<String>?,
    var postDate: String,
    var posterName: String,
    var posterImageUrl: String,
    var peopleIdTags: ArrayList<String>?,
    var type: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readArrayList(LikeModel::class.java.classLoader) as ArrayList<LikeModel>,
        parcel.readArrayList(CommentModel::class.java.classLoader) as ArrayList<CommentModel>,
        parcel.readArrayList(String::class.java.classLoader) as ArrayList<String>,

        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readArrayList(String::class.java.classLoader) as ArrayList<String>?,
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(postId)
        parcel.writeString(posterId)
        parcel.writeString(postText)
        parcel.writeString(postPossition)
        parcel.writeString(postDate)
        parcel.writeString(posterName)
        parcel.writeString(posterImageUrl)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostModel> {
        override fun createFromParcel(parcel: Parcel): PostModel {
            return PostModel(parcel)
        }

        override fun newArray(size: Int): Array<PostModel?> {
            return arrayOfNulls(size)
        }
    }
}