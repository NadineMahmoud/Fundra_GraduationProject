package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.fundra.databinding.ActivityDonationBinding
import com.example.fundra.databinding.ActivitySortingBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class DonationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonationBinding

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityDonationBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.baheya.setOnClickListener {
                    val intent = Intent(this, BaheyaActivity::class.java)
                    startActivity(intent)
                }

                binding.filter.setOnClickListener {
                    val bottomSheet = Sorting_BottomSheet_Dialog()
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


