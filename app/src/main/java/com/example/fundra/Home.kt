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
import com.example.fundra.menu.Account_Activity
import com.example.fundra.menu.ChatBotActivity
import com.example.fundra.menu.Wallet_Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notification.setOnClickListener{
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Users")

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
        binding.donation.setOnClickListener {
            val intent = Intent(this, DonationActivity::class.java)
            startActivity(intent)
        }

    }
    @SuppressLint("SetTextI18n")
    private fun updateUserName() {
        val userText: TextView = findViewById(R.id.userText)
        val userNameFromIntent = intent.getStringExtra("userName")

        if (!userNameFromIntent.isNullOrEmpty()) {
            userText.text = "Hello, $userNameFromIntent"
            Log.d("FirebaseDebug", "Received Name in Home from Intent: $userNameFromIntent")
        } else {
            val userID = firebaseAuth.currentUser?.uid
            if (userID != null) {
                database.child(userID).child("name").get()
                    .addOnSuccessListener { dataSnapshot ->
                        val userName = dataSnapshot.getValue(String::class.java) ?: "New User"
                        userText.text = "Hello, $userName"
                        Log.d("FirebaseDebug", "Fetched Name from Firebase: $userName")
                    }
                    .addOnFailureListener {
                        Log.e("FirebaseDebug", "Failed to retrieve user name")
                        userText.text = "Hello, New User"
                    }
            } else {
                userText.text = "Hello, New User"
                Log.d("FirebaseDebug", "User ID is null")
            }
        }
    }


    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.fragment_enter,
            0
        )
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
