package com.example.fundra

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.balance.Payment_Methods_Activity
import com.example.fundra.databinding.ActivityInstaPayBinding

class InstaPayActivity : AppCompatActivity() {
    private lateinit var binding :  ActivityInstaPayBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInstaPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            Payment_Methods_Activity().show(supportFragmentManager, "PaymentMethods")
        }
        binding.copyOne.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", binding.copeidOne.text.toString())
            clipboard.setPrimaryClip(clip)

            binding.copyTwo.setOnClickListener {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", binding.copiesTwo.text.toString())
                clipboard.setPrimaryClip(clip)

                binding.copyThree.setOnClickListener {
                    val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("label", binding.copiedThree.text.toString())
                    clipboard.setPrimaryClip(clip)
                }
                binding.copied.setOnClickListener {
                    val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("label", binding.copied.text.toString())
                    clipboard.setPrimaryClip(clip)
                }
                binding.copy.setOnClickListener {
                    val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("label", binding.copied.text.toString())
                    clipboard.setPrimaryClip(clip)
                }
            }
            Toast.makeText(this, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
        }
    }
}