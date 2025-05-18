package com.example.fundra.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.account.AccountAdapter
import com.example.fundra.*
import com.example.fundra.databinding.ActivityAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Account_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.backButton.setOnClickListener {
            val intent : Intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val userID = firebaseAuth.currentUser?.uid
        if (userID != null) {
            database.child(userID).get().addOnSuccessListener { dataSnapshot ->
                val userName = dataSnapshot.child("name").getValue(String::class.java) ?: "Unknown"
                val email = dataSnapshot.child("email").getValue(String::class.java) ?: "No email"

                val phone = dataSnapshot.child("phone").getValue(String::class.java) ?: "No phone"
                val social = dataSnapshot.child("socialMediaLinks").getValue(String::class.java) ?: "No social"

                binding.userName.text = userName
                binding.userEmail.text = email

                val items = listOf(
                    AccountItem(
                        R.drawable.ic_contact,
                        "Personal details",
                        userName,
                        email,
                        phone,
                        social
                    ),
                    AccountItem(R.drawable.ic_notification, "Notification"),
                    AccountItem(R.drawable.saved, "Saved"),
                    AccountItem(R.drawable.ic_deposit, "Deposit"),
                    AccountItem(R.drawable.ic_investments, "Investments"),
                    AccountItem(R.drawable.community, "Community"),
                    AccountItem(R.drawable.campaign, "Add Campaign")
                )

                val adapter = AccountAdapter(items) { item ->
                    when (item.text) {
                        "Notification" -> startActivity(Intent(this, NotificationActivity::class.java))
                        "Saved" -> startActivity(Intent(this, SavedActivity::class.java))
                        "Deposit" -> startActivity(Intent(this, Wallet_Activity::class.java))
                        "Investments" -> startActivity(Intent(this, InvestmentsActivity::class.java))
                        "Community" -> startActivity(Intent(this, CommunityActivity::class.java))
                        "Add Campaign" -> startActivity(Intent(this, AddCampaignActivity::class.java))
                    }
                }

                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.adapter = adapter
            }
        }
    }
}
