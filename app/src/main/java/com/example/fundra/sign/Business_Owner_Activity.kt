package com.example.fundra.sign

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.Home
import com.example.fundra.R
import com.example.fundra.databinding.ActivityBusinessOwnerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Business_Owner_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessOwnerBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val FILE_PICKER_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val pass = intent.getStringExtra("password")

        binding = ActivityBusinessOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Users")

        val types = listOf("Reward-Based", "Revenue-Based", "Donation-Based")
        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, types)
        binding.InvestorReturnTypeET.setAdapter(adapter)

        val stage = listOf("MVP ready", "Operating business", "Generating revenue")
        val adapter2 = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, stage)
        binding.CurrentStageDrop.setAdapter(adapter2)

        binding.PrivacyPolicies.setOnClickListener {
            val bottomSheet = Privacy_Polices_Activity()
            bottomSheet.show(supportFragmentManager, "PrivacySheet")
        }

        val uploadButton = findViewById<Button>(R.id.upload)
        uploadButton.setOnClickListener {
            openFileChooser()
        }

        binding.signUpBtn.setOnClickListener {
            val businessName = binding.BusinessNamelET.text.toString().trim()
            val businessDescription = binding.BusinessDescriptionET.text.toString().trim()
            val businessCategory = binding.BusinessCategoryET.text.toString().trim()
            val currentStage = binding.CurrentStageDrop.text.toString().trim()
            val investorReturnType = binding.InvestorReturnTypeET.text.toString().trim()
            val teamMembers = binding.TeamMembersDrop.text.toString().trim()
            val socialMedia = binding.SocialMediaLinksET.text.toString().trim()

            if (businessName.isEmpty() || businessDescription.isEmpty() || businessCategory.isEmpty() || currentStage.isEmpty()
                || investorReturnType.isEmpty() || teamMembers.isEmpty() || socialMedia.isEmpty()
            ) {
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
                        Toast.makeText(this, "Business Info Saved", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Home::class.java)
                        intent.putExtra("userName", name)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to save business data", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(Intent.createChooser(intent, "Select a file"), FILE_PICKER_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val fileUri: Uri? = data?.data
            if (fileUri != null) {
                Toast.makeText(this, "Selected: ${fileUri.path}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
