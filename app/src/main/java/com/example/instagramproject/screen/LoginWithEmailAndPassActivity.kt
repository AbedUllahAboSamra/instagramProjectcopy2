package com.example.instagramproject.screen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramproject.databinding.ActivityLoginWithEmailAndPassBinding
import com.example.instagramproject.model.CustomProgressDialog
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginWithEmailAndPassActivity : AppCompatActivity() {
    //
    //
    //
    // initialisation
    lateinit var binding: ActivityLoginWithEmailAndPassBinding
    lateinit var auth: FirebaseAuth
    lateinit var callbackManager: CallbackManager
    var progressDialog = CustomProgressDialog()

    // initialisation
    //
    //
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginWithEmailAndPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        binding.btnLogin.isEnabled = false


        binding.btnLoginWithFaceBook.setPermissions("email", "public_profile")
        //      LoginManager.getInstance().logInWithReadPermissions(this, arrayListOf("email","public_profile"))
        binding.btnLoginWithFaceBook.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("ASD", "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d("ASD", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("ASD", "facebook:onError", error)
            }
        })


    }

    override fun onStart() {
        super.onStart()

// btn enable or not

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtEmailOrNameOrPhone.text!!.isNotEmpty() && p0!!.isNotEmpty() && p0.length >= 6) {
                    binding.btnLogin.isEnabled = true
                } else {
                    binding.btnLogin.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.edtEmailOrNameOrPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtPassword.text!!.isNotEmpty() && binding.edtPassword.text!!.length >= 6 && p0!!.isNotEmpty()) {
                    binding.btnLogin.isEnabled = true
                } else {
                    binding.btnLogin.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

//btn login clicked
        binding.btnLogin.setOnClickListener {
            progressDialog.show(this, "")
            var email = binding.edtEmailOrNameOrPhone.text.toString()
            var password = binding.edtPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { it ->


                    var shared = getSharedPreferences("My", MODE_PRIVATE)
                    var editor = shared.edit()
                    editor.putString("uId", it.user!!.uid)
                    editor.apply()
                    if (it.user!!.uid == "") {
                        progressDialog.dialog.dismiss()
                        startActivity(Intent(this, MainActivity::class.java))
                        this.finish()
                    } else {
                        progressDialog.dialog.dismiss()
                        Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { it ->
                    progressDialog.dialog.dismiss()
                    Toast.makeText(this, "Email or password invalid", Toast.LENGTH_SHORT).show()
                }

        }


//btn btn_create_account clicked
        binding.btnCreateAccount.setOnClickListener {
            val i = Intent(this, SignUp1Activity::class.java)
            startActivity(i)
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}