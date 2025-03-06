package com.example.fundra

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivitySignInBinding
import com.example.fundra.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class Sign_up : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpText.setOnClickListener {
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
            finish()
        }
        binding.signUpBtn.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }

}