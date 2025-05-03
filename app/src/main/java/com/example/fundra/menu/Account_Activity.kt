package com.example.fundra.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.account.AccountAdapter
import com.example.fundra.*
import com.example.fundra.databinding.ActivityAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Account_Activity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ViewBinding setup
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView setup
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val items = listOf(
            AccountItem(R.drawable.ic_contact, "Personal details"),
            AccountItem(R.drawable.ic_notification, "Notification"),
            AccountItem(R.drawable.saved, "Saved"),
            AccountItem(R.drawable.ic_deposit, "Deposit"),
            AccountItem(R.drawable.ic_investments, "Investments"),
            AccountItem(R.drawable.community, "Community")
        )

        val adapter = AccountAdapter(items) { item ->
            when (item.text) {
                "Notification" -> startActivity(Intent(this, NotificationActivity::class.java))
                "Saved" -> startActivity(Intent(this, SavedActivity::class.java))
                "Deposit" -> startActivity(Intent(this, DepositActivity::class.java))
                "Investments" -> startActivity(Intent(this, InvestmentsActivity::class.java))
                "Community" -> startActivity(Intent(this, CommunityActivity::class.java))
            }
        }
        binding.recyclerView.adapter = adapter

        updateUserName()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUserName() {
        val userText = binding.userName
        val userEmail = binding.userEmail
        val userNameFromIntent = intent.getStringExtra("userName")
        val emailFromIntent = intent.getStringExtra("email")

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Users")

        if (!userNameFromIntent.isNullOrEmpty() && !emailFromIntent.isNullOrEmpty()) {
            userText.text = userNameFromIntent
            userEmail.text = emailFromIntent
            Log.d("FirebaseDebug", "Received from Intent - Name: $userNameFromIntent, Email: $emailFromIntent")
        } else {
            val userID = firebaseAuth.currentUser?.uid
            if (userID != null) {
                database.child(userID).get()
                    .addOnSuccessListener { dataSnapshot ->
                        val userName = dataSnapshot.child("name").getValue(String::class.java) ?: ""
                        val email = dataSnapshot.child("email").getValue(String::class.java) ?: ""
                        userText.text = userName
                        userEmail.text = email
                        Log.d("FirebaseDebug", "Fetched from Firebase - Name: $userName, Email: $email")
                    }
                    .addOnFailureListener {
                        Log.e("FirebaseDebug", "Failed to retrieve user data")
                        userText.text = "New User"
                        userEmail.text = ""
                    }
            } else {
                userText.text = "New User"
                userEmail.text = ""
                Log.d("FirebaseDebug", "User ID is null")
            }
        }
    }
}
