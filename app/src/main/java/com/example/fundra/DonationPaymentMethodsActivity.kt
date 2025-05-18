package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.balance.AddMoneyActivity
import com.example.fundra.balance.vodafon_addMony_Activity
import com.example.fundra.databinding.ActivityDonationPaymentMethodsBinding
import com.example.fundra.databinding.ActivityPaymentMethodsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DonationPaymentMethodsActivity : BottomSheetDialogFragment() {
    private var _binding: ActivityDonationPaymentMethodsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ActivityDonationPaymentMethodsBinding.inflate(inflater, container, false)

        binding.vodafoneArrow.setOnClickListener{
            startActivity(Intent(requireActivity(), VodafoneDonationActivity::class.java))
        }
        binding.instaArrow.setOnClickListener{
            startActivity(Intent(requireActivity(), InstaPayDonationActivity::class.java))
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = (resources.displayMetrics.heightPixels * 0.5).toInt()
    }
}