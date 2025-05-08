package com.example.fundra

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivityBusinessOwnerBinding

class Business_Owner_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBusinessOwnerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val types = listOf("Reward-Based", "Revenue-Based", "Donation-Based")
        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        binding.InvestorReturnTypeET.setAdapter(adapter)

        val stage = listOf("MVP ready","Operating business","Generating revenue")
        val adapter2 = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, stage)
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_1)
        binding.CurrentStageDrop.setAdapter(adapter2)

    }
}