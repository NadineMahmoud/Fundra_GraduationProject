package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityAddMoneyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddMoneyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMoneyBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            userId = currentUser.uid
            loadCurrentBalance(userId!!)
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }

        binding.continueBtn.setOnClickListener {
                val balanceText = binding.balanceTX.text.toString()
                val newBalance = balanceText.toDoubleOrNull()

                if (newBalance != null && newBalance > 0) {
                    val fee = newBalance * 0.01 // 1% fee
                    val finalBalance = newBalance - fee

                    // إرسال الرصيد إلى AddMoneyCardActivity
                    val intent = Intent(this, AddMoneyCardActivity::class.java)
                    intent.putExtra("currentBalance", finalBalance) // إرسال الرصيد بعد خصم الرسوم
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                }
            }
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, Wallet_Activity::class.java))
        }
    }

    private fun loadCurrentBalance(userId: String) {
        database.child(userId).child("balance").get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val balance = snapshot.value.toString().toDoubleOrNull() ?: 0.0
                binding.currentalance.text = "$$balance"
            } else {
                binding.currentalance.text = "$0.00"
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load balance", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateBalance(userId: String, amountToAdd: Double) {
        database.child(userId).child("balance").get().addOnSuccessListener { snapshot ->
            val currentBalance = snapshot.value.toString().toDoubleOrNull() ?: 0.0
            val updatedBalance = currentBalance + amountToAdd

            database.child(userId).child("balance").setValue(updatedBalance)
                .addOnSuccessListener {
                    binding.currentalance.text = "$$updatedBalance"
                    binding.balanceTX.text.clear()
                    Toast.makeText(this, "Balance updated successfully!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update balance", Toast.LENGTH_SHORT).show()
                }
        }.addOnFailureListener {
            Toast.makeText(this, "Error fetching balance", Toast.LENGTH_SHORT).show()
        }
    }
}
