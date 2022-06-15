package com.example.instagramproject.screen


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramproject.R
import com.example.instagramproject.adapter.adapterPagerFilesPick
import com.example.instagramproject.databinding.ActivityFirstCreatePostBinding


class FirstCreatePostActivity : AppCompatActivity() {
    lateinit var binding: ActivityFirstCreatePostBinding

    var PICK_IMAGE_MULTIPLE = 125

    companion object {
        var mArrayUri = ArrayList<Uri>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.to_ToolBar))
        supportActionBar!!.title = ""

        // pick image or video from gallery
        val intent = Intent()
        intent.type = "image/* video/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_MULTIPLE
        )

        binding.vPagerView.pageMargin = 15
        binding.vPagerView.setPadding(50, 0, 50, 0);
        binding.vPagerView.setClipToPadding(false)
        binding.vPagerView.setPageMargin(25)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_MULTIPLE && null != data) {

            if (data.clipData != null) {
                binding.vPagerView.visibility = View.VISIBLE
                binding.btnNext.visibility = View.VISIBLE

                var cout = data.clipData!!.itemCount;

                for (i in 0 until cout) {

                    var imageurl = data.clipData!!.getItemAt(i).uri
                    Log.e("ASD", imageurl.toString())

                    mArrayUri.add(imageurl)
                }
            } else {
                binding.vPagerView.visibility = View.VISIBLE
                binding.btnNext.visibility = View.VISIBLE

                var imageurl = data.data
                mArrayUri.add(imageurl!!)
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    override fun onStart() {
        super.onStart()

        if (mArrayUri.isNotEmpty()) {
            binding.btnNext.visibility = View.VISIBLE
            binding.vPagerView.visibility = View.VISIBLE

            var adapter = adapterPagerFilesPick(this, mArrayUri)
            binding.vPagerView.adapter = adapter
        } else {
            binding.vPagerView.visibility = View.GONE
            binding.btnNext.visibility = View.GONE

        }

        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, ScandCreatePostActivity::class.java))
        }
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}
