package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Sign_in : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.signInText.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
            finish()
        }

        binding.forgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPass::class.java)
            startActivity(intent)
            finish()
        }

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
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
                            val userId = firebaseAuth.currentUser?.uid
                            if (userId != null) {
                                getUserBalance(userId) // ✅ استدعاء دالة جلب الرصيد
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Error: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("FirebaseAuth", "Exception: ${task.exception?.message}")
                        }
                    }
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserBalance(userId: String) {
        database.child(userId).child("balance").get()
            .addOnSuccessListener { snapshot ->
                val balance = snapshot.getValue(Double::class.java) ?: 0.0

                val intent = Intent(this, Home::class.java)
                intent.putExtra("userBalance", balance)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Failed to fetch balance: ${exception.message}")
                Toast.makeText(this, "Failed to fetch balance", Toast.LENGTH_SHORT).show()
            }
    }
}
