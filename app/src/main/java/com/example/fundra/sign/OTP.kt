package com.example.fundra.sign

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.R
import com.example.fundra.databinding.ActivityOtpBinding

class OTP : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_otp)

        binding.backButton.setOnClickListener {
            val intent = Intent(this, ForgetPass::class.java)
            startActivity(intent)
            finish()
        }


    }
}