package com.example.fundra

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityFirstCompanyBinding
import com.example.fundra.menu.Account_Activity
import com.example.fundra.menu.ChatBotActivity
import com.example.fundra.menu.Wallet_Activity

class FirstCompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstCompanyBinding
    private var totalAmount = 350000
    private var totalDonors = 799
    private val goalAmount = 500000
    private var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        val sharedPreferences = getSharedPreferences("SavedCards", MODE_PRIVATE)

        // Restore saved state
        isSaved = sharedPreferences.getBoolean("immersed_visible", false)
        updateSaveButtonUI()

        // Back button
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
        }

        // Invest button
        binding.investBtn.setOnClickListener {
            startActivity(Intent(this, InvestActivity::class.java))
        }

        // Save button
        binding.save.setOnClickListener {
            isSaved = !isSaved
            sharedPreferences.edit().putBoolean("immersed_visible", isSaved).apply()
            updateSaveButtonUI()
        }

        // Bottom navigation
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

    private fun updateSaveButtonUI() {
        binding.save.setImageResource(if (isSaved) R.drawable.saved else R.drawable.save)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isSaved", isSaved)
    }
}