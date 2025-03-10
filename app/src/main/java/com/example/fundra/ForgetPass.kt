package com.example.fundra

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityForgetPassBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ForgetPass : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPassBinding
    private var email = ""
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.resetBtn.setOnClickListener {
            email = binding.emailET.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailET.error = "Enter your email"
                return@setOnClickListener
            }

            // Show progress bar and hide button
            binding.resetBtn.visibility = View.GONE
            binding.pBar.visibility = View.VISIBLE

            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                binding.resetBtn.visibility = View.VISIBLE
                binding.pBar.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    "Password reset sent to your $email address.",
                    Snackbar.ANIMATION_MODE_SLIDE
                ).show()
            }.addOnFailureListener {
                binding.resetBtn.visibility = View.VISIBLE
                binding.pBar.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    "Error: ${it.message}",
                    Snackbar.ANIMATION_MODE_SLIDE
                ).show()
            }
        }
    }
}
