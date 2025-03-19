package com.example.fundra

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Sign_up : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.signUpText.setOnClickListener {
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
            finish()
        }

        binding.signUpBtn.setOnClickListener {
            val email = binding.emailET.text.toString().trim()
            val name = binding.namelET.text.toString().trim()
            val password = binding.passwordET.text.toString().trim()
            val isChecked = binding.checkbox.isChecked

            if (!isChecked) {
                Toast.makeText(
                    this,
                    "Terms must be agreed to before registering!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = firebaseAuth.currentUser?.uid
                            if (userId != null) {
                                val user = mapOf(
                                    "fullName" to name,
                                    "email" to email,
                                    "balance" to 100000.0
                                )

                                database.child(userId).setValue(user)
                                    .addOnSuccessListener {
                                        // تحديث الـ displayName في FirebaseAuth
                                        val userProfile = UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .build()

                                        firebaseAuth.currentUser?.updateProfile(userProfile)
                                            ?.addOnCompleteListener { profileUpdateTask ->
                                                if (profileUpdateTask.isSuccessful) {
                                                    Toast.makeText(
                                                        this,
                                                        "Successfully registered",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    startActivity(Intent(this, Home::class.java))
                                                    finish()
                                                } else {
                                                    Toast.makeText(
                                                        this,
                                                        "Failed to register",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "فشل في حفظ بيانات المستخدم",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "خطأ: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("FirebaseAuth", "Exception: ${task.exception?.message}")
                        }
                    }
            } else {
                Toast.makeText(this, "لا يُمكن ترك الحقول فارغة!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUserName() {
        val userText: TextView = findViewById(R.id.userText)
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        userText.text = if (user != null && !user.displayName.isNullOrEmpty()) {
            "Hello، ${user.displayName}"
        } else {
            "Hello , new user"
        }
    }
}