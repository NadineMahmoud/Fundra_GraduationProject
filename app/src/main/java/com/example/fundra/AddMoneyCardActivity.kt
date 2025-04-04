package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivityAddMoneyCardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class AddMoneyCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMoneyCardBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMoneyCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, AddMoneyActivity::class.java))
        }
    }
        private fun listenForBalanceUpdates(userId: String) {
            database.child(userId).child("balance").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val balance = snapshot.getValue(Double::class.java) ?: 0.0
                    binding.currentalance.text = "$$balance" // تحديث الرصيد في الشاشة
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AddMoneyCardActivity, "Failed to load balance", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }