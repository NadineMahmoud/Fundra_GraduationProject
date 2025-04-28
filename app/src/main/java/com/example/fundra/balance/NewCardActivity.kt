package com.example.fundra.balance

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.menu.Wallet_Activity
import com.example.fundra.databinding.ActivityNewCardBinding

class NewCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this, CardsActivity::class.java)
            startActivity(intent)
        }
        binding.saveBtn.setOnClickListener {
            val name = binding.cardholderTX.text.toString().trim()
            val number = binding.cardNumberTX.text.toString().trim()
            val date = binding.expireTX.text.toString().trim()
            val cvv = binding.cvvTX.text.toString().trim()

            if (name.isNotEmpty() && number.isNotEmpty()&& date.isNotEmpty()&& cvv.isNotEmpty()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, Wallet_Activity::class.java))
                    finish()
                },2000)
                Toast.makeText(this, "Card Saved Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed!", Toast.LENGTH_SHORT).show()
            }
            }
    }
}