package com.example.fundra

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivityAddCampaignBinding
import com.google.android.material.snackbar.Snackbar

class AddCampaignActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCampaignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddCampaignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.warning.setOnClickListener {
            val snackbar = Snackbar.make(
                it,
                "After reviewing your company's specifications, we will respond via email. This will take from 1 to 7 days.",
                Snackbar.LENGTH_LONG
            )
            snackbar.view.apply {
                setBackgroundResource(R.drawable.snakebar)
            }
            snackbar.show()
        }
    }
}
