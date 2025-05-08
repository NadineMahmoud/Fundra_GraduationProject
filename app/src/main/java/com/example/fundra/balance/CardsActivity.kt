package com.example.fundra.balance

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.fundra.FancyCardTransformer
import com.example.fundra.R
import com.example.fundra.databinding.ActivityCardsBinding
import com.example.fundra.menu.Wallet_Activity
class CardsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardsBinding
    private val bankNames = listOf("Banque Misr", "QNB", "CIB")
    private val radio_images = listOf(R.drawable.bank_one_t_image, R.drawable.bank_three_image, R.drawable.bank_one_t_image)
    private val bankBalances = listOf("100,000", "220,000", "134,000")
    private val bankImages = listOf(
        R.drawable.card_one,
        R.drawable.card_two,
        R.drawable.card_three
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val balanceTextView = binding.balance
        val bankNameTextView = binding.currentBankName
        val bankImageView = binding.currentBankImage
        val radioButton = binding.currentBankRadio

        val adapter = BankImageAdapter(bankImages)
        binding.imageSlider.adapter = adapter

        // Apply custom animation
        binding.imageSlider.setPageTransformer(FancyCardTransformer())

        // Update bank name and balance based on page
        binding.imageSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.balance.text = bankBalances[position]
                bankNameTextView.text = bankNames[position]
                bankImageView.setImageResource(radio_images[position])
                radioButton.isChecked = false
            }
        })

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            startActivity(Intent(this, Wallet_Activity::class.java))
            finish()
        }

        binding.AddCard.setOnClickListener {
            startActivity(Intent(this, NewCardActivity::class.java))
            finish()
        }
    }

    class BankImageAdapter(private val images: List<Int>) :
        RecyclerView.Adapter<BankImageAdapter.BankImageViewHolder>() {

        inner class BankImageViewHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankImageViewHolder {
            val imageView = ImageView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
            return BankImageViewHolder(imageView)
        }

        override fun onBindViewHolder(holder: BankImageViewHolder, position: Int) {
            (holder.itemView as ImageView).setImageResource(images[position])
        }

        override fun getItemCount(): Int = images.size
    }
}