package com.example.fundra

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivityHomeBinding
import com.example.fundra.databinding.ActivitySignInBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
            finish()
        }
        binding.companies.setOnClickListener{
            val intent = Intent(this, ChatBotGimini::class.java)
            startActivity(intent)
            finish()
        }

    }
}