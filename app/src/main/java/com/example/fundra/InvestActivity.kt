package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivityInvestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class InvestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInvestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInvestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.credit.setOnClickListener {
            val intent = Intent(this, PaymentMethod_firstCompanyActivity::class.java)
            startActivity(intent)
        }
        binding.investBtn.setOnClickListener {
            val amountText = binding.amountET.text.toString()

            if (amountText.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null || amount < 1) {
                Toast.makeText(this, "You must enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tax = amount * 0.0001  // 0.01% tax
            val totalAmount = amount + tax

            // Determine star points based on the amount
            val starPoints = when {
                amount in 1.0..100.0 -> 5
                amount in 101.0..500.0 -> 10
                amount in 501.0..1000.0 -> 15
                amount in 1000.0..2000.0 -> 20
                amount in 2000.0..5000.0 -> 30
                amount in 5000.0..10000.0 -> 50
                amount in 10000.0..50000.0 -> 100
                amount in 50000.0..100000.0 -> 200
                else -> 300
            }

            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val uid = currentUser.uid
            val userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)
            val balanceRef = userRef.child("balance")
            val starsRef = userRef.child("points")

            balanceRef.get().addOnSuccessListener { snapshot ->
                val currentBalance = snapshot.getValue(Double::class.java) ?: 0.0
                Log.d("Firebase", "Current Balance: $currentBalance")

                if (currentBalance < totalAmount) {
                    Toast.makeText(this, "Insufficient balance (after tax)", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val newBalance = currentBalance - totalAmount

                    // Update balance
                    balanceRef.setValue(newBalance)
                        .addOnSuccessListener {
                            // Update stars
                            starsRef.get().addOnSuccessListener { starSnap ->
                                val currentStars = starSnap.getValue(Int::class.java) ?: 0
                                val newStars = currentStars + starPoints
                                starsRef.setValue(newStars)
                            }

                            Toast.makeText(
                                this,
                                "Successful Investment!\nTax: $tax\nYou earned $starPoints stars ⭐",
                                Toast.LENGTH_LONG
                            ).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to update balance", Toast.LENGTH_SHORT)
                                .show()
                        }

                    Log.d("Firebase", "New Balance: $newBalance | Stars Earned: $starPoints")
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error retrieving wallet balance", Toast.LENGTH_SHORT).show()
            }
        }
    }
}