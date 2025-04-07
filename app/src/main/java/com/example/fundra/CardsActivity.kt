package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class CardsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

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

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
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
                page.translationX = -position * 40 // تراكب الكروت
            }
            addTransformer(StackZoomTransformer()) // التكبير والتصغير
        }
        viewPager.setPageTransformer(transformer)

        // مهم علشان الكروت تظهر في الجوانب
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
    }
}
