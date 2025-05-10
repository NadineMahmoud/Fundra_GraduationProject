package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityBusinessOwnerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Business_Owner_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessOwnerBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val pass = intent.getStringExtra("password")

        binding = ActivityBusinessOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.PrivacyPolicies.setOnClickListener {
            val bottomSheet = Privacy_Polices_Activity()
            bottomSheet.show(supportFragmentManager, "PrivacySheet")
        }

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Users")

        val types = listOf("Reward-Based", "Revenue-Based", "Donation-Based")
        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, types)
        binding.InvestorReturnTypeET.setAdapter(adapter)

        val stage = listOf("MVP ready", "Operating business", "Generating revenue")
        val adapter2 = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, stage)
        binding.CurrentStageDrop.setAdapter(adapter2)

        binding.signUpBtn.setOnClickListener {
            val businessName = binding.BusinessNamelET.text.toString().trim()
            val businessDescription = binding.BusinessDescriptionET.text.toString().trim()
            val businessCategory = binding.BusinessCategoryET.text.toString().trim()
            val currentStage = binding.CurrentStageDrop.text.toString().trim()
            val investorReturnType = binding.InvestorReturnTypeET.text.toString().trim()
            val teamMembers = binding.TeamMembersDrop.text.toString().trim()
            val socialMedia = binding.SocialMediaLinksET.text.toString().trim()

            if (businessName.isEmpty() || businessDescription.isEmpty() || businessCategory.isEmpty() || currentStage.isEmpty()
                || investorReturnType.isEmpty() || teamMembers.isEmpty() || socialMedia.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = firebaseAuth.currentUser
            val userID = user?.uid

            if (userID != null) {
                val businessData = hashMapOf(
                    "businessName" to businessName,
                    "businessDescription" to businessDescription,
                    "businessCategory" to businessCategory,
                    "currentStage" to currentStage,
                    "investorReturnType" to investorReturnType,
                    "teamMembers" to teamMembers,
                    "socialMediaLinks" to socialMedia
                )

                database.child(userID).child("BusinessDetails").setValue(businessData)
                    .addOnSuccessListener {
                        Log.d("FirebaseDebug", "Business data saved successfully!")
                        Toast.makeText(this, "Business Info Saved", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, Home::class.java)
                        intent.putExtra("userName", name)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to save business data", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
