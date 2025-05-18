package com.example.fundra

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.balance.vodafon_addMony_Activity
import com.example.fundra.databinding.ActivityVodafoneReceiptBinding

class vodafone_Activity_receipt : AppCompatActivity() {
    private lateinit var binding: ActivityVodafoneReceiptBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVodafoneReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this, vodafon_addMony_Activity::class.java)
            startActivity(intent)
        }

    }
}