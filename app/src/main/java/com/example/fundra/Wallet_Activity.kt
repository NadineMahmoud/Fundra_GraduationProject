package com.example.fundra

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivityWalletBinding

class Wallet_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.add.setOnClickListener {
            startActivity(Intent(this, AddMoneyActivity::class.java))
        }
        binding.with.setOnClickListener {
            startActivity(Intent(this, WithdrawlActivity::class.java))
        }
        binding.card.setOnClickListener {
            startActivity(Intent(this, CardsActivity::class.java))
        }
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
        }
    }
}