package com.example.instagramproject.screen

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramproject.databinding.ActivityScandCreatePostBinding
import com.example.instagramproject.model.CustomProgressDialog
import com.example.instagramproject.model.PostModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ScandCreatePostActivity : AppCompatActivity() {
    lateinit var binding: ActivityScandCreatePostBinding
    val stroge = Firebase.storage
    val strogeRef = stroge.reference.child("images")
    var progressDialog = CustomProgressDialog()

    companion object {
        var mBitMap = ArrayList<Bitmap>()
    }

    var arrayUrlPostImages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScandCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        for (i in FirstCreatePostActivity.mArrayUri) {
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, i)
            mBitMap.add(bitmap)
        }

    }

    override fun onStart() {
        super.onStart()
        binding.img.setImageURI(FirstCreatePostActivity.mArrayUri[0])
        binding.btnAddLocation.setOnClickListener {
            startActivity(Intent(this, AddLocationActivity::class.java))
        }
        binding.btnAddTagPeople.setOnClickListener {
            startActivity(Intent(this, TagPeopleActivity::class.java))

        }
        binding.btnShare.setOnClickListener {
            progressDialog.show(this, "Lodding")
            var size = mBitMap.size
            var count = 0
            val childref =
                strogeRef.child(System.currentTimeMillis().toString() + "ASDImages")

            for (i in mBitMap) {
                count++
                val baos = ByteArrayOutputStream()
                i.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                var uploadTask = childref.putBytes(data)

                uploadTask.addOnFailureListener {
                    Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener { taskSnap ->
                    childref.downloadUrl.addOnSuccessListener { it ->
                        Log.e("ASD", it.toString())
                        arrayUrlPostImages.add(it.toString())
                        if (count == size ) {
                            Handler().postDelayed({
Log.e("ASD","postDelayed")

                                var postMap = HashMap<String, Any>()
                                postMap["posterId"] = SplachActivity.uId
                                postMap["postText"] = binding.edtPostContent.text.toString()
                                postMap["postDate"] = Date().date.toString()
                                postMap["posterImageUrl"] =
                                    "https://i.picsum.photos/id/782/200/300.jpg?grayscale&hmac=pVq8q45ZnyWOPDP3vRLdOwswl3w3koNTFoGtUI8GVjE"
                                postMap["posterName"] = "Abod Ayman0"
                                postMap["postPossition"] = Date().date.toString()


                                FirebaseFirestore.getInstance()
                                    .collection("posts")
                                    .add(postMap)
                                    .addOnSuccessListener { it ->

                                        var uirMap = HashMap<String, String>()
                                        for (i in 0 until arrayUrlPostImages.size) {
                                            uirMap[i.toString()] = arrayUrlPostImages[i]
                                            Log.e("ASD", arrayUrlPostImages[i])
                                            Log.e("ASD", uirMap.isNotEmpty().toString())
                                            Log.e("ASD", uirMap["$i"]?:"ASD")

                                        }
                                        FirebaseFirestore.getInstance()
                                            .collection("posts")
                                            .document(it.id)
                                            .collection("postImagesUrl")
                                            .add(uirMap)
                                            .addOnSuccessListener {
                                                startActivity(Intent(this, MainActivity::class.java))
                                                finish()
                                            }


                                    }.addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Check your connection",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                            }, 2000)
                        }

                    }
                }


            }
        }
    }
}
