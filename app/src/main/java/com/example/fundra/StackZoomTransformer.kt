package com.example.fundra

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class StackZoomTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width

        when {
            position < -1 -> { // الكارت خارج الشاشة على الشمال
                page.alpha = 0f
            }
            position <= 1 -> {
                val scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f
                val translationX = -position * pageWidth * 0.2f
                val translationY = Math.abs(position) * 30f

                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
                page.translationX = translationX
                page.translationY = translationY
                page.alpha = 1f
            }
            else -> { // الكارت خارج الشاشة على اليمين
                page.alpha = 0f
            }
        }
    }
}
