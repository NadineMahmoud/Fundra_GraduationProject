package com.example.fundra.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.Home
import com.example.fundra.Payment_Methods_Activity
import com.example.fundra.R
import com.example.fundra.WithdrawlActivity
import com.example.fundra.balance.AddMoneyActivity
import com.example.fundra.balance.CardsActivity
import com.example.fundra.databinding.ActivityWalletBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Wallet_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWalletBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

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
            Payment_Methods_Activity().show(supportFragmentManager, "PaymentMethods")
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

    override fun onResume() {
        super.onResume()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            listenForBalanceUpdates(currentUser.uid)
        }
    }

    private fun listenForBalanceUpdates(userId: String) {
        database.child(userId).child("balance").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val balance = snapshot.getValue(Double::class.java) ?: 0.0
                binding.balance.text = "$$balance" // تحديث الرصيد على الشاشة
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Wallet_Activity, "Failed to load balance", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
