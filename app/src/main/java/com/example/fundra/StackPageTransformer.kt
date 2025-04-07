package com.example.fundra

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class StackPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.apply {
            val absPos = Math.abs(position)
            scaleY = 1 - (0.05f * absPos)
            translationY = 30f * absPos
            translationX = -30f * position
            alpha = 1 - (0.1f * absPos)
        }
    }
}
