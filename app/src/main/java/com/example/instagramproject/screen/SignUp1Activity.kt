package com.example.instagramproject.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagramproject.databinding.ActivitySignUp1Binding

class SignUp1Activity : AppCompatActivity() {

    // init
    lateinit var binding: ActivitySignUp1Binding

    //finish  init
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUp1Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginWithEmailAndPassActivity::class.java))
        }
        binding.btnSignUpWithEmailOrPhone.setOnClickListener {
            startActivity(Intent(this, SignUp2Activity::class.java))
        }
    }
}