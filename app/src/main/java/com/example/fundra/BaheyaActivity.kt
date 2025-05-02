package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.Donation,
            android.R.layout.simple_spinner_item
        )

        val items =
            listOf("All", "Zakah Mal", "Kafalah Mareed", "Chemotheraphy Kafalah", "Special causes")

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter2

        binding.backButton.setOnClickListener {
            val intent = Intent(this, DonationActivity::class.java)
            startActivity(intent)
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = items[position]
                Toast.makeText(this@BaheyaActivity, "Cause Selected: $selected", Toast.LENGTH_SHORT)
                    .show()

                if (selected == "Special causes") {
                    val intent = Intent(this@BaheyaActivity, SpecialCausesActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
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
                Toast.makeText(this, "Amount must be between 5 and 60000 EGP", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = currentUser.uid
            val userWalletRef =
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("balance")

            userWalletRef.get().addOnSuccessListener { snapshot ->
                val currentBalance = snapshot.getValue(Double::class.java) ?: 0.0
                Log.d("Firebase", "Current Balance: $currentBalance")

                if (currentBalance < amount) {
                    Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show()
                } else {
                    val newBalance = currentBalance - amount
                    userWalletRef.setValue(newBalance)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Donation successful. Thank you!", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to update balance", Toast.LENGTH_SHORT).show()
                        }
                    Log.d("Firebase", "New Balance: $newBalance")
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error retrieving wallet balance", Toast.LENGTH_SHORT).show()
            }
        }
    }
}