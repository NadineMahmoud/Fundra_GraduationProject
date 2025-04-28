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

        // رجوع للـ Wallet
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

        val lottieView = findViewById<LottieAnimationView>(R.id.cardsAnimationr)
        val radioButton1 = findViewById<RadioButton>(R.id.radioButton1)
        val radioButton2 = findViewById<RadioButton>(R.id.radioButton2)
        val radioButton3 = findViewById<RadioButton>(R.id.radioButton3)

        var isAnimationStarted = false  // علشان نتأكد من إنه بدأ يتحرك مرة واحدة

        lottieView.setOnClickListener {
            if (!isAnimationStarted) {
                lottieView.playAnimation()  // يبدأ الأنميشن
                isAnimationStarted = true
            }

            // إضافة التفاعل مع الـ Radio Button حسب مكان الضغط في الأنميشن
            lottieView.addAnimatorUpdateListener { animation ->
                val progress = animation.animatedFraction

                // بناءً على التقدم في الأنميشن، هنغير الـ Radio Button
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

        val viewPager: ViewPager2 = findViewById(R.id.cardsAnimationr)
        viewPager.setPageTransformer(StackZoomTransformer())

        val images = listOf(
            R.drawable.card_one,
            R.drawable.card_two,
            R.drawable.card_three
        )

        val adapter = CardsAdapter(images)
        viewPager.adapter = adapter

        // هنا نعرّف العناصر يدويًا
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

        // الحصول على الـ RadioButtons و الـ CardViews
        val radioCard1 = findViewById<View>(R.id.radio1)
        val radioCard2 = findViewById<View>(R.id.radio2)
        val radioCard3 = findViewById<View>(R.id.radio3)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // تغيير مكان الكارد المحدد بناءً على الـ position
                when (position) {
                    0 -> { // إذا كانت الصورة الخاصة بالكارد الأول
                        radioCard1.visibility = View.VISIBLE // إظهار radioCard1
                        radioCard2.visibility = View.INVISIBLE // إخفاء radioCard2
                        radioCard3.visibility = View.INVISIBLE // إخفاء radioCard3
                    }

                    1 -> { // إذا كانت الصورة الخاصة بالكارد الثاني
                        radioCard1.visibility = View.INVISIBLE // إخفاء radioCard1
                        radioCard2.visibility = View.VISIBLE // إظهار radioCard2
                        radioCard3.visibility = View.INVISIBLE // إخفاء radioCard3
                    }

                    2 -> { // إذا كانت الصورة الخاصة بالكارد الثالث
                        radioCard1.visibility = View.INVISIBLE // إخفاء radioCard1
                        radioCard2.visibility = View.INVISIBLE // إخفاء radioCard2
                        radioCard3.visibility = View.VISIBLE // إظهار radioCard3
                    }

                    else -> { // القيمة الافتراضية (في حالة عدم وجود أي صورة محددة)
                        radioCard1.visibility = View.VISIBLE // إظهار radioCard1
                        radioCard2.visibility = View.INVISIBLE // إخفاء radioCard2
                        radioCard3.visibility = View.INVISIBLE // إخفاء radioCard3
                    }
                }

                // ✅ نحدّث البلانس حسب الكارت
                val balance = when (position) {
                    0 -> "100000"
                    1 -> "220000"
                    2 -> "134000"
                    else -> "100000"
                }
                balanceTextView.text = balance
            }
        })

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        val balanceTX = findViewById<TextView>(R.id.balance)

        val userId = firebaseAuth.currentUser?.uid
        userId?.let {
            database.child(it).child("balance")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val balance = snapshot.getValue(Double::class.java) ?: 0.0
                        balanceTX.text = "$$balance"
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@CardsActivity,
                            "Failed to load balance",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            listenForBalanceUpdates(currentUser.uid)
        }
    }

    private fun listenForBalanceUpdates(userId: String) {
        database.child(userId).child("balance").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val balance = snapshot.getValue(Double::class.java) ?: 0.0
                binding.balance.text = "$$balance"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CardsActivity, "Failed to load balance", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
