package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityForgetPassBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetPass : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPassBinding
    private lateinit var emailET: EditText
    private lateinit var resetBtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_forget_pass)

        auth = FirebaseAuth.getInstance()
        emailET = findViewById(R.id.emailET)
        resetBtn = findViewById(R.id.resetBtn)

        resetBtn.setOnClickListener {
            val email = emailET.text.toString().trim()
            if (email.isNotEmpty()) {
                sendPasswordResetEmail(email)
            } else {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
            }
        }
        binding.go.setOnClickListener {
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Password reset link sent to your email.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
