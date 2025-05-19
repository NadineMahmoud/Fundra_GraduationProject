package com.example.fundra

import Invest_Projects
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivityFirstCompanyBinding
import com.example.fundra.databinding.ActivityFirstProjectBinding
import com.example.fundra.menu.Account_Activity
import com.example.fundra.menu.ChatBotActivity
import com.example.fundra.menu.Wallet_Activity

class FirstProjectActivity : AppCompatActivity() {
            private lateinit var binding: ActivityFirstProjectBinding
            private var totalAmount = 350000
            private var totalDonors = 799
            private val goalAmount = 500000
            private var isSaved = false
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityFirstProjectBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.backButton.setOnClickListener {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
            binding.investBtn.setOnClickListener {
                val intent = Intent(this, rewardBasedActivity::class.java)
                startActivity(intent)
            }
            binding.menuNav.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_home -> {
                        startActivity(Intent(this, Home::class.java))
                        true
                    }
                    R.id.navigation_chatbot -> {
                        startActivity(Intent(this, ChatBotActivity::class.java))
                        true
                    }
                    R.id.navigation_balance -> {
                        startActivity(Intent(this, Wallet_Activity::class.java))
                        true
                    }
                    R.id.navigation_account -> {
                        startActivity(Intent(this, Account_Activity::class.java))
                        true
                    }
                    else -> false
                }
            }
        }
    }