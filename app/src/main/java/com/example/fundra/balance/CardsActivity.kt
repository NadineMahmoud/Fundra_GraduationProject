package com.example.fundra.balance

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.fundra.R
import com.example.fundra.StackZoomTransformer
import com.example.fundra.menu.Wallet_Activity
import com.example.fundra.databinding.ActivityCardsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class CardsActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityCardsBinding

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            val intent = Intent(this, Wallet_Activity::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<TextView>(R.id.AddCard).setOnClickListener {
            val intent = Intent(this, NewCardActivity::class.java)
            startActivity(intent)
            finish()
        }

        val lottieView = binding.cardsAnimationr
        val radioButton1 = findViewById<RadioButton>(R.id.radioButton1)
        val radioButton2 = findViewById<RadioButton>(R.id.radioButton2)
        val radioButton3 = findViewById<RadioButton>(R.id.radioButton3)

        var isAnimationStarted = false

        lottieView.setOnClickListener {
            if (!isAnimationStarted) {
                lottieView.playAnimation()
                isAnimationStarted = true
            }

            lottieView.addAnimatorUpdateListener { animation ->
                val progress = animation.animatedFraction

                when {
                    progress < 0.33 -> {
                        radioButton1.isChecked = true
                        radioButton2.isChecked = false
                        radioButton3.isChecked = false
                    }

                    progress < 0.66 -> {
                        radioButton1.isChecked = false
                        radioButton2.isChecked = true
                        radioButton3.isChecked = false
                    }

                    else -> {
                        radioButton1.isChecked = false
                        radioButton2.isChecked = false
                        radioButton3.isChecked = true
                    }
                }
            }
        }

// ViewPager setup
        val viewPager: ViewPager2 = findViewById(R.id.cardsAnimationr)
        viewPager.setPageTransformer(StackZoomTransformer())

        val images = listOf(
            R.drawable.card_one,
            R.drawable.card_two,
            R.drawable.card_three
        )

        val adapter = CardsAdapter(images)
        viewPager.adapter = adapter

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val balanceTextView = findViewById<TextView>(R.id.balance)
        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.dots_indicator)
        dotsIndicator.setViewPager2(viewPager)

        val transformer = CompositePageTransformer().apply {
            addTransformer { page, position ->
                page.translationX = -position * 40
            }
            addTransformer(StackZoomTransformer())
        }
        viewPager.setPageTransformer(transformer)

        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false

        val radioCard1 = findViewById<View>(R.id.radio1)
        val radioCard2 = findViewById<View>(R.id.radio2)
        val radioCard3 = findViewById<View>(R.id.radio3)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 -> {
                        radioCard1.visibility = View.VISIBLE
                        radioCard2.visibility = View.INVISIBLE
                        radioCard3.visibility = View.INVISIBLE
                    }

                    1 -> {
                        radioCard1.visibility = View.INVISIBLE
                        radioCard2.visibility = View.VISIBLE
                        radioCard3.visibility = View.INVISIBLE
                    }

                    2 -> {
                        radioCard1.visibility = View.INVISIBLE
                        radioCard2.visibility = View.INVISIBLE
                        radioCard3.visibility = View.VISIBLE
                    }

                    else -> {
                        radioCard1.visibility = View.VISIBLE
                        radioCard2.visibility = View.INVISIBLE
                        radioCard3.visibility = View.INVISIBLE
                    }
                }

                val balance = when (position) {
                    0 -> "100000"
                    1 -> "220000"
                    2 -> "134000"
                    else -> "100000"
                }
                balanceTextView.text = balance
            }
        })
    }
}