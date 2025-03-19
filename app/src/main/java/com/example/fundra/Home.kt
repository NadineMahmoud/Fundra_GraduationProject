package com.example.fundra

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fundra.Fragments.*
import com.example.fundra.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUserName()

        showFragment(CompaniesFragment())

        binding.companies.setOnClickListener {
            showFragment(CompaniesFragment())
        }
        binding.projects.setOnClickListener {
            showFragment(ProjectsFragment())
        }
        binding.health.setOnClickListener {
            showFragment(HealthFragments())
        }
        binding.edu.setOnClickListener {
            showFragment(EducationFragment())
        }
        binding.donation.setOnClickListener {
            showFragment(DonationFragment())
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

    @SuppressLint("SetTextI18n")
    private fun updateUserName() {
        val userText: TextView = findViewById(R.id.userText)

        // استقبال الاسم من Intent
        val userName = intent.getStringExtra("userName") ?: "New User"

        Log.d("FirebaseDebug", "Received Name in Home: $userName") // ✅ طباعة الاسم في الـ Logcat

        userText.text = "Hello, $userName"
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
