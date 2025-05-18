package com.example.fundra.balance

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.fundra.R
import com.example.fundra.menu.Wallet_Activity
import com.example.fundra.databinding.ActivityAddMoneyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
                val fee = newBalance * 0.001
                val finalBalance = newBalance - fee

                updateBalance(userId!!, finalBalance)

                startActivity(Intent(this, Wallet_Activity::class.java))
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, Wallet_Activity::class.java))
        }

        val items = listOf(
            mapOf("image" to R.drawable.bank_one_t_image, "text" to "3129"),
            mapOf("image" to R.drawable.bank_three_image, "text" to "3328")
        )
        val spinner = findViewById<AutoCompleteTextView>(R.id.classification_drop)

        val adapter = SimpleAdapter(
            this,
            items,
            R.layout.spinner,
            arrayOf("image", "text"),
            intArrayOf(R.id.item_icon, R.id.spinnerItemText)
        )
        spinner.setAdapter(adapter)
        spinner.inputType = InputType.TYPE_NULL

        spinner.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = items[position]
            val imageRes = selectedItem["image"] as Int
            val text = selectedItem["text"] as String

            val drawable = ContextCompat.getDrawable(this, imageRes)
            drawable?.setBounds(0, 0, 60, 60)
            spinner.setText(text, false)
            spinner.setCompoundDrawables(drawable, null, null, null)
        }
        spinner.inputType = InputType.TYPE_NULL
    }

        private fun loadCurrentBalance(userId: String) {
            database.child(userId).child("balance")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val balance = snapshot.getValue(Double::class.java) ?: 0.0
                            Log.d("AddMoneyActivity", "Loaded balance: $balance")
                            binding.currentalance.text = "$$balance"
                        } else {
                            Log.d("AddMoneyActivity", "No balance found")
                            binding.currentalance.text = "$0.00"
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@AddMoneyActivity,
                            "Failed to load balance",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("AddMoneyActivity", "Error fetching balance: ${error.message}")
                    }
                })
        }

    private fun updateBalance(userId: String, amountToAdd: Double) {
        database.child(userId).child("balance")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currentBalance = snapshot.getValue(Double::class.java) ?: 0.0
                    val updatedBalance = currentBalance + amountToAdd

                    database.child(userId).child("balance").setValue(updatedBalance)
                        .addOnSuccessListener {
                            binding.currentalance.text = "$$updatedBalance"
                            binding.balanceTX.text.clear()
                            Toast.makeText(
                                this@AddMoneyActivity,
                                "Balance updated successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this@AddMoneyActivity,
                                "Failed to update balance",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@AddMoneyActivity,
                        "Error fetching balance",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
