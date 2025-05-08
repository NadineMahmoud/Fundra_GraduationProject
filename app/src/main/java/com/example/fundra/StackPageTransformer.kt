package com.example.fundra

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class FancyCardTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.apply {
            val absPos = abs(position)

            when {
                position < -1 || position > 1 -> {
                    alpha = 0f
                }
                else -> {
                    // الشفافية
                    alpha = 1 - absPos * 0.3f

                    // التحجيم
                    val scale = 0.85f + (1 - absPos) * 0.15f
                    scaleX = scale
                    scaleY = scale

                    // انزلاق خفيف لليمين/الشمال
                    translationX = -position * width * 0.3f

                    // دوران خفيف لمسة فنية 😎
                    rotation = position * 5f

                    // ارتفاع طفيف
                    translationY = absPos * 40f
                }
            }
        }
    }
}
