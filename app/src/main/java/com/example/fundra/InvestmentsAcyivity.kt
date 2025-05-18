package com.example.fundra

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityInvestmentsBinding
import com.example.fundra.menu.Account_Activity
import com.example.fundra.menu.ChatBotActivity
import com.example.fundra.menu.Wallet_Activity

class InvestmentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInvestmentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this, Account_Activity::class.java)
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
