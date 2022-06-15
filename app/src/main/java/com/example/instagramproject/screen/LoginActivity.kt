package com.example.instagramproject.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.instagramproject.R
import com.example.instagramproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    // init start :: :: ::
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    // init end :: :: ::
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


    }

    override fun onStart() {
        binding.btnLogIntoAnotherAccount.setOnClickListener {
            var i = Intent(this, LoginWithEmailAndPassActivity::class.java)
            startActivity(i)
        }
        binding.btnCreateAccount.setOnClickListener {
            var i = Intent(this, SignUp1Activity::class.java)
            startActivity(i)
        }

        super.onStart()
    }
}