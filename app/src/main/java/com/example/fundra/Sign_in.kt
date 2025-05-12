package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.fundra.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Sign_in : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        val passwordET = findViewById<TextInputEditText>(R.id.passwordET)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordLayout)

        passwordET.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                passwordLayout.hint = ""
            } else {
                passwordLayout.hint = "Enter Your Password"
            }
        }


        binding.signUPText.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
            finish()
        }
        passwordET.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                passwordLayout.hint = "Enter your password"
            } else {
                passwordLayout.hint = null
            }
        }
        binding.forgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPass::class.java)
            startActivity(intent)
            finish()
        }

        binding.SignInBtn.setOnClickListener {
            val email = binding.emailET.text.toString().trim()
            val password = binding.passwordET.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userID = firebaseAuth.currentUser?.uid
                            if (userID != null) {
                                database.child(userID).get()
                                    .addOnSuccessListener { snapshot ->
                                        val userName = snapshot.child("name").getValue(String::class.java) ?: "New User"
                                        val balance = snapshot.child("balance").getValue(Double::class.java) ?: 0.0

                                        val intent = Intent(this, Home::class.java)
                                        intent.putExtra("userName", userName)
                                        intent.putExtra("userBalance", balance)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Log.e("FirebaseDebug", "Failed to fetch user data in Sign_in: ${it.message}")
                                        startActivity(Intent(this, Home::class.java))
                                        finish()
                                    }
                            } else {
                                startActivity(Intent(this, Home::class.java))
                                finish()
                            }

                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            Log.e("FirebaseAuth", "Exception: ${task.exception?.message}")
                        }
                    }
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
