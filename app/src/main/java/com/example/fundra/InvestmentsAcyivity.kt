package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityInvestmentsBinding
import com.example.fundra.menu.Account_Activity
import com.example.fundra.menu.ChatBotActivity
import com.example.fundra.menu.Wallet_Activity
class InvestmentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInvestmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button
        binding.backButton.setOnClickListener {
            val intent = Intent(this, Account_Activity::class.java)
            startActivity(intent)
        }

        // Bottom navigation
        binding.menuNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, Home::class.java))
                    true
                }
                R.id.navigation_chatbot -> {
                    startActivity(Intent(this, ChatBotActivity::class.java))
                    true
                }
                R.id.navigation_balance -> {
                    startActivity(Intent(this, Wallet_Activity::class.java))
                    true
                }
                R.id.navigation_account -> {
                    startActivity(Intent(this, Account_Activity::class.java))
                    true
                }
                else -> false
            }
        }

        // Set up report buttons
        setupReportButtons()
    }

    private fun setupReportButtons() {
        val reportButtons = listOf(
            binding.firstCard.findViewById<TextView>(R.id.report_one),
            binding.secondCard.findViewById<TextView>(R.id.report_two),
            binding.thirdCard.findViewById<TextView>(R.id.report_three)
        )

        val reportImages = listOf(
            R.drawable.report,
            R.drawable.report,
            R.drawable.report
        )

        reportButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                showReportDialog(reportImages.getOrElse(index) { R.drawable.report })
            }
        }
    }

    private fun showReportDialog(imageRes: Int) {
        val dialogView = layoutInflater.inflate(R.layout.activity_report, null)
        val reportImage = dialogView.findViewById<ImageView>(R.id.report_image)
        val closeButton = dialogView.findViewById<Button>(R.id.close_button)

        try {
            reportImage.setImageResource(imageRes)
        } catch (e: Exception) {
            reportImage.setImageResource(R.drawable.report)
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
