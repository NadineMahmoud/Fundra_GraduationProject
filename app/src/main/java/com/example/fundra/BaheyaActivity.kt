package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityBaheyaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class BaheyaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaheyaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaheyaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val amountEditText = binding.amountEditText
        val donateButton = binding.donateBtn

        val items = listOf(
            "Donation Cause",
            "All",
            "Zakat al-Mal",
            "Patient Sponsorship",
            "Chemotherapy Sponsorship",
            "Special causes"
        )

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)

        binding.spinner.setAdapter(adapter2)

        binding.spinner.setOnItemClickListener { parent, view, position, id ->
            val selected = adapter2.getItem(position) ?: ""
            Toast.makeText(this, "Cause Selected: $selected", Toast.LENGTH_SHORT).show()

            if (selected == "Special causes") {
                val intent = Intent(this, Special_CausesActivity::class.java)
                startActivity(intent)
            }
        }
        binding.backButton.setOnClickListener {
            val intent = Intent(this, DonationActivity::class.java)
            startActivity(intent)
        }
        binding.donateBtn.setOnClickListener {
            val bottomSheetDialog = com.google.android.material.bottomsheet.BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.activity_donation_payment_methods, null)
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                val selected = adapter2.getItem(position) ?: ""
                Toast.makeText(this@BaheyaActivity, "Cause Selected: $selected", Toast.LENGTH_SHORT).show()

                if (selected == "Special causes") {
                    val intent = Intent(this@BaheyaActivity, Special_CausesActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No action needed
            }
        }

        donateButton.setOnClickListener {
            val amountText = amountEditText.text.toString()

            if (amountText.isEmpty()) {
                Toast.makeText(
                    this, "Please enter a valid amount between 5 and 60000 EGP",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null || amount < 5 || amount > 60000) {
                Toast.makeText(this, "Amount must be between 5 and 60000 EGP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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
            val userWalletRef = userRef.child("balance")
            val userStarsRef = userRef.child("points")

            userWalletRef.get().addOnSuccessListener { snapshot ->
                val currentBalance = snapshot.getValue(Double::class.java) ?: 0.0
                Log.d("Firebase", "Current Balance: $currentBalance")

                if (currentBalance < amount) {
                    Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show()
                } else {
                    val newBalance = currentBalance - amount
                    userWalletRef.setValue(newBalance)
                        .addOnSuccessListener {
                            userStarsRef.get().addOnSuccessListener { starsSnapshot ->
                                val currentStars = starsSnapshot.getValue(Int::class.java) ?: 0
                                val newStars = currentStars + starPoints
                                userStarsRef.setValue(newStars)
                            }

                            Toast.makeText(
                                this,
                                "Donation successful. You earned $starPoints stars ⭐",
                                Toast.LENGTH_LONG
                            ).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to update balance", Toast.LENGTH_SHORT).show()
                        }

                    Log.d("Firebase", "New Balance: $newBalance | Stars Earned: $starPoints")
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error retrieving wallet balance", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
