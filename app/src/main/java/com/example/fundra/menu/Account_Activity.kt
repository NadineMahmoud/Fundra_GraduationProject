package com.example.fundra.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.account.AccountAdapter
import com.example.fundra.AccountItem
import com.example.fundra.CommunityActivity
import com.example.fundra.DepositActivity
import com.example.fundra.InvestmentsActivity
import com.example.fundra.NotificationActivity
import com.example.fundra.R
import com.example.fundra.SavedActivity

class Account_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)

            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            val items = listOf(
                AccountItem(R.drawable.ic_contact, "Personal details"),
                AccountItem(R.drawable.ic_notification, "Notification"),
                AccountItem(R.drawable.saved, "Saved"),
                AccountItem(R.drawable.ic_deposit, "Deposit"),
                AccountItem(R.drawable.ic_investments, "Investments"),
                AccountItem(R.drawable.community, "Community")
            )

            val adapter = AccountAdapter(items) { item ->
                when (item.text) {
                    "Notification" -> startActivity(Intent(this, NotificationActivity::class.java))
                    "Saved" -> startActivity(Intent(this, SavedActivity::class.java))
                    "Deposit" -> startActivity(Intent(this, DepositActivity::class.java))
                    "Investments" -> startActivity(Intent(this, InvestmentsActivity::class.java))
                    "Community" -> startActivity(Intent(this, CommunityActivity::class.java))
                }
            }
            recyclerView.adapter = adapter
        }
    }
