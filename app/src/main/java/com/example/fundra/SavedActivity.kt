package com.example.savedscreen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.fundra.R

class SavedActivity : AppCompatActivity() {

    private lateinit var viewFlipper: ViewFlipper
    private lateinit var backButton: ImageButton
    private lateinit var companiesArrow: ImageView
    private lateinit var projectsArrow: ImageView
    private lateinit var immersedCard: CardView
    private lateinit var sybertechCard: CardView
    private lateinit var odaiCard: CardView
    private lateinit var immersedUnsave: Button
    private lateinit var sybertechUnsave: Button
    private lateinit var odaiUnsave: Button
    private lateinit var companiesImagesContainer: LinearLayout
    private lateinit var companiesImagesStack: LinearLayout
    private lateinit var companyImage1Container: CardView
    private lateinit var companyImage2Container: CardView
    private lateinit var companyImage3Container: CardView
    private lateinit var companyImage1: ImageView
    private lateinit var companyImage2: ImageView
    private lateinit var companyImage3: ImageView
    private lateinit var companiesAddsCount: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("SavedCards", Context.MODE_PRIVATE)

        // Initialize Views
        viewFlipper = findViewById(R.id.view_flipper)
        backButton = findViewById(R.id.back_button)
        companiesArrow = findViewById(R.id.companies_arrow)
        projectsArrow = findViewById(R.id.projects_arrow)
        immersedCard = findViewById(R.id.immersed_card)
        sybertechCard = findViewById(R.id.sybertech_card)
        odaiCard = findViewById(R.id.odai_card)
        immersedUnsave = immersedCard.findViewById(R.id.unsave_button)
        sybertechUnsave = sybertechCard.findViewById(R.id.unsave_button)
        odaiUnsave = odaiCard.findViewById(R.id.unsave_button)
        companiesImagesContainer = findViewById(R.id.companies_images_container)
        companiesImagesStack = findViewById(R.id.companies_images_stack)
        companyImage1Container = findViewById(R.id.company_image_1_container)
        companyImage2Container = findViewById(R.id.company_image_2_container)
        companyImage3Container = findViewById(R.id.company_image_3_container)
        companyImage1 = findViewById(R.id.company_image_1)
        companyImage2 = findViewById(R.id.company_image_2)
        companyImage3 = findViewById(R.id.company_image_3)
        companiesAddsCount = findViewById(R.id.companies_adds_count)

        // Setup Cards in Second Page
        setupCard(immersedCard, R.drawable.immersed, R.drawable.immersed_second)
        setupCard(sybertechCard, R.drawable.sybertech, R.drawable.sybertech_second)
        setupCard(odaiCard, R.drawable.artifical, R.drawable.intelligent)

        // Restore Card Visibility from SharedPreferences
        immersedCard.visibility = if (sharedPreferences.getBoolean("immersed_visible", true)) View.VISIBLE else View.GONE
        sybertechCard.visibility = if (sharedPreferences.getBoolean("sybertech_visible", true)) View.VISIBLE else View.GONE
        odaiCard.visibility = if (sharedPreferences.getBoolean("odai_visible", true)) View.VISIBLE else View.GONE

        // Update Companies Images
        updateCompaniesImages()

        // Companies Arrow Click Listener
        companiesArrow.setOnClickListener {
            viewFlipper.showNext() // Show Second Page (Companies Cards)
        }
        // Projects Arrow Click Listener
        projectsArrow.setOnClickListener {
            // Add logic for Projects cards if needed
        }

        // Back Button Click Listener
        backButton.setOnClickListener {
            if (viewFlipper.currentView == viewFlipper.getChildAt(1)) {
                viewFlipper.showPrevious() // Return to First Page (Categories)
            } else {
                finish() // Return to Previous Activity
            }
        }

        // Unsave Button Click Listeners
        immersedUnsave.setOnClickListener {
            immersedCard.visibility = View.GONE
            sharedPreferences.edit().putBoolean("immersed_visible", false).apply()
            updateCompaniesImages()
        }

        sybertechUnsave.setOnClickListener {
            sybertechCard.visibility = View.GONE
            sharedPreferences.edit().putBoolean("sybertech_visible", false).apply()
            updateCompaniesImages()
        }

        odaiUnsave.setOnClickListener {
            odaiCard.visibility = View.GONE
            sharedPreferences.edit().putBoolean("odai_visible", false).apply()
            updateCompaniesImages()
        }
    }

    // Function to Setup Cards in Second Page
    private fun setupCard(card: CardView, imageRes: Int, textImageRes: Int) {
        val imageView = card.findViewById<ImageView>(R.id.card_image)
        val textImageView = card.findViewById<ImageView>(R.id.card_text_image)

        imageView.setImageResource(imageRes)
        textImageView.setImageResource(textImageRes)
    }

    // Function to Update Companies Images Based on Visible Cards
    private fun updateCompaniesImages() {
        val visibleCards = mutableListOf<Int>()
        if (immersedCard.visibility == View.VISIBLE) visibleCards.add(R.drawable.immersed)
        if (sybertechCard.visibility == View.VISIBLE) visibleCards.add(R.drawable.sybertech)
        if (odaiCard.visibility == View.VISIBLE) visibleCards.add(R.drawable.artifical)

        // Update Adds Count
        companiesAddsCount.text = "${visibleCards.size} Adds"

        // Reset Images Based on Visible Cards Count
        when (visibleCards.size) {
            0 -> {
                companiesImagesContainer.visibility = View.GONE
            }
            1 -> {
                companiesImagesContainer.visibility = View.VISIBLE
                companyImage1Container.visibility = View.VISIBLE
                companiesImagesStack.visibility = View.GONE
                companyImage1.setImageResource(visibleCards[0])
            }
            2 -> {
                companiesImagesContainer.visibility = View.VISIBLE
                companyImage1Container.visibility = View.VISIBLE
                companiesImagesStack.visibility = View.VISIBLE
                companyImage2Container.visibility = View.VISIBLE
                companyImage3Container.visibility = View.GONE
                companyImage1.setImageResource(visibleCards[0])
                companyImage2.setImageResource(visibleCards[1])
            }
            else -> {
                companiesImagesContainer.visibility = View.VISIBLE
                companyImage1Container.visibility = View.VISIBLE
                companiesImagesStack.visibility = View.VISIBLE
                companyImage2Container.visibility = View.VISIBLE
                companyImage3Container.visibility = View.VISIBLE
                companyImage1.setImageResource(visibleCards[0])
                companyImage2.setImageResource(visibleCards[1])
                companyImage3.setImageResource(visibleCards[2])
            }
        }
    }
}