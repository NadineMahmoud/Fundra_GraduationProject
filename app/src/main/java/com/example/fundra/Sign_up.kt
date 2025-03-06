package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class Sign_up : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signUpText.setOnClickListener {
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
            finish()
        }

        // 🔹 زر التسجيل
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailET.text.toString().trim()
            val name = binding.namelET.text.toString().trim()
            val password = binding.passwordET.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            finish()
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
}
