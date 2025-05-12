package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.fundra.databinding.ActivityDonationBinding
import com.example.fundra.menu.Account_Activity
import com.example.fundra.menu.ChatBotActivity
import com.example.fundra.menu.Wallet_Activity

class DonationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonationBinding


            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityDonationBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.backButton.setOnClickListener {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                }

                binding.baheya.setOnClickListener {
                    val intent = Intent(this, BaheyaActivity::class.java)
                    startActivity(intent)
                }

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
                binding.filter.setOnClickListener {
                    val bottomSheet = Sorting_BottomSheet_Dialog_activity()
                    bottomSheet.filterListener = object : OnFilterSelectedListener {
                        override fun onFilterSelected(type: String) {
                            when (type) {
                                "All" -> showAllCharityImages()
                                else -> filterCharityImagesByType(type)
                            }
                        }
                    }
                    bottomSheet.show(supportFragmentManager, "SortingSheet")
                }

            }

            fun filterCharityImagesByType(type: String) {
                val container = findViewById<ConstraintLayout>(R.id.donation_container)


                for (i in 0 until container.childCount) {
                    val child = container.getChildAt(i)
                    val tag = child.tag?.toString()
                    child.visibility = if (tag == type) View.VISIBLE else View.GONE
                }
            }

            fun showAllCharityImages() {
                val container = findViewById<LinearLayout>(R.id.donation_container)
                for (i in 0 until container.childCount) {
                    container.getChildAt(i).visibility = View.VISIBLE
                }
            }

            interface OnFilterSelectedListener {
                fun onFilterSelected(type: String)
            }



        }


