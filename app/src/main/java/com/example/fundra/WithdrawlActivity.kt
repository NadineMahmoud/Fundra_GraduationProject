package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivityAddMoneyBinding
import com.example.fundra.databinding.ActivityWithdrawlBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WithdrawlActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWithdrawlBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawlBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, Wallet_Activity::class.java))
        }

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
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
                binding.currentalance.text = "$$balance" // تحديث الرصيد على الشاشة
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@WithdrawlActivity, "Failed to load balance", Toast.LENGTH_SHORT).show()
            }
        })
    }
}