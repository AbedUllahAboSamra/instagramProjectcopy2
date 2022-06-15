package com.example.instagramproject.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.instagramproject.databinding.ActivitySignUp2Binding
import com.example.instagramproject.model.CustomProgressDialog
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp2Activity : AppCompatActivity() {
    // initialization
    lateinit var binding: ActivitySignUp2Binding
    var liveNumOfStep = MutableLiveData<Int>()
    var email = ""
    var phone = ""
    var userName = ""
    var passWord = ""
    var accountNAme = ""
    lateinit var auth: FirebaseAuth
    private val progressDialog = CustomProgressDialog()

    // initialization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUp2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // add values to lateinit
        liveNumOfStep.value = 0
        auth = FirebaseAuth.getInstance()




        binding.stpFirstStepLeaner.visibility = View.VISIBLE
        binding.btnArrowBack.visibility = View.VISIBLE
        binding.btnNext.isEnabled = false
        binding.btnNextFinalStep.isEnabled = false
        binding.btnNextTherdStep.isEnabled = false
        binding.btnNextScandStep.isEnabled = false


        // code to enable the button next or not
        binding.edtPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 6) {
                    binding.btnNext.isEnabled = true

                } else {
                    binding.btnNext.isEnabled = false

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 10 && p0.contains("@") && p0.contains(".")) {
                    binding.btnNext.isEnabled = true
                } else {
                    binding.btnNext.isEnabled = false

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 6) {
                    binding.btnNextScandStep.isEnabled = true
                } else {
                    binding.btnNextScandStep.isEnabled = false

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 6) {
                    binding.btnNextTherdStep.isEnabled = true
                } else {
                    binding.btnNextTherdStep.isEnabled = false

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.edtUserNameDescribtion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.length > 6) {
                    binding.btnNextFinalStep.isEnabled = true
                } else {
                    binding.btnNextFinalStep.isEnabled = false

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        // code to activate the tab layout
        binding.tabTapLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text == "phone" || tab.text == "رقم الهاتف") {
                    binding.layPhone.visibility = View.VISIBLE
                    binding.edtEmail.visibility = View.GONE
                } else {
                    binding.layPhone.visibility = View.GONE
                    binding.edtEmail.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


    }

    override fun onStart() {
        super.onStart()
        liveNumOfStep.observe(this) { it ->
            when (it) {
                0 -> {
                    binding.stpFirstStepLeaner.visibility = View.VISIBLE
                    binding.scendStepLieanerLayout.visibility = View.GONE
                    binding.theardStepLieanerLayout.visibility = View.GONE
                    binding.forthStepLieanerLayout.visibility = View.GONE
                }
                1 -> {
                    binding.stpFirstStepLeaner.visibility = View.GONE
                    binding.scendStepLieanerLayout.visibility = View.VISIBLE
                    binding.theardStepLieanerLayout.visibility = View.GONE
                    binding.forthStepLieanerLayout.visibility = View.GONE
                    binding.btnArrowBack.visibility = View.INVISIBLE

                }
                2 -> {
                    binding.stpFirstStepLeaner.visibility = View.GONE
                    binding.scendStepLieanerLayout.visibility = View.GONE
                    binding.theardStepLieanerLayout.visibility = View.VISIBLE
                    binding.forthStepLieanerLayout.visibility = View.GONE
                    binding.btnArrowBack.visibility = View.INVISIBLE

                }
                3 -> {
                    binding.stpFirstStepLeaner.visibility = View.GONE
                    binding.scendStepLieanerLayout.visibility = View.GONE
                    binding.theardStepLieanerLayout.visibility = View.GONE
                    binding.forthStepLieanerLayout.visibility = View.VISIBLE
                    binding.btnArrowBack.visibility = View.INVISIBLE
                }
            }
        }
        // value of step set on click
        binding.btnNext.setOnClickListener {
            email = binding.edtEmail.text.toString()
            phone = binding.edtPhone.text.toString()
            if (phone.isNotEmpty() || email.isNotEmpty()) {
                liveNumOfStep.value = liveNumOfStep.value!! + 1

            }


        }
        binding.btnNextScandStep.setOnClickListener {
            userName = binding.edtName.text.toString()
            if (userName.isNotEmpty()) {
                liveNumOfStep.value = liveNumOfStep.value!! + 1

            }
        }
        binding.btnNextTherdStep.setOnClickListener {
            passWord = binding.edtPassword.text.toString()

            if (passWord.isNotEmpty()) {
                liveNumOfStep.value = liveNumOfStep.value!! + 1

            }
        }
        binding.btnNextFinalStep.setOnClickListener {
            accountNAme = binding.edtUserNameDescribtion.text.toString()
            if (accountNAme.isNotEmpty()
                && userName.isNotEmpty()
                && passWord.isNotEmpty()
                && accountNAme.isNotEmpty()
            ) {
                auth.createUserWithEmailAndPassword(email, passWord)
                    .addOnSuccessListener { user ->
                        progressDialog.show(this)
                        val map = HashMap<String, Any>()
                        map["accountName"] = accountNAme
                        map["email"] = email
                        map["password"] = passWord
                        map["userName"] = userName

                        FirebaseFirestore.getInstance().collection("users")
                            .document(user.user!!.uid)
                            .set(map)
                            .addOnSuccessListener {
                                progressDialog.dialog.dismiss()
                                startActivity(Intent(this, MainActivity::class.java))
                                if (binding.savePasswordBox.isChecked) {
                                    val pref = getSharedPreferences("My", MODE_PRIVATE)
                                    val editor = pref.edit()
                                    editor.putString("uId", user.user!!.uid)
                                    editor.apply()
                                }
                                this.finish()
                            }.addOnFailureListener {
                                liveNumOfStep.value = 0
                                progressDialog.dialog.dismiss()
                                Toast.makeText(this, "Failed operation", Toast.LENGTH_SHORT).show()
                            }

                    }
                    .addOnFailureListener {
                    }

            }
        }

        //btn back to last activity
        binding.btnArrowBack.setOnClickListener {
            this.onBackPressed()
        }


    }
}