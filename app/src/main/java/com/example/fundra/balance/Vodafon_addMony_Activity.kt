package com.example.fundra.balance

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityVodafonAddMonyBinding
import com.example.fundra.vodafone_Activity_receipt

class vodafon_addMony_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityVodafonAddMonyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVodafonAddMonyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueBtn.setOnClickListener {
            val intent = Intent(this, vodafone_Activity_receipt::class.java)
            startActivity(intent)
        }
        binding.backButton.setOnClickListener{
            val intent = Intent(this, Payment_Methods_Activity::class.java)
            startActivity(intent)
        }


    }
}