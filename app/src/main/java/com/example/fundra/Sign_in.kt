package com.example.fundra

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class Sign_in : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signInText.setOnClickListener {
            startActivity(Intent(this, Sign_up::class.java))
            finish()
        }
        binding.SignInBtn.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}