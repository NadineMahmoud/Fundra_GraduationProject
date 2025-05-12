package com.example.fundra

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivityVodafonAddMonyBinding

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
            val intent = Intent(this,vodafone_Activity_receipt::class.java)
            startActivity(intent)
        }




    }
}