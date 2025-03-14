package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
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
                Toast.makeText(this, "Terms must be agreed to before registering!", Toast.LENGTH_SHORT).show()
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
                                    "password" to password,
                                    "balance" to 100000.0
                                )

                                database.child(userId).setValue(user).addOnSuccessListener {
                                    Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, Home::class.java))
                                    finish()
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                                }
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
