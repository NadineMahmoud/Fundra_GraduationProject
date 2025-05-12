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
import com.example.fundra.Sorting_BottomSheet_Dialog_activity.DeleteVisibilityListener
import com.example.fundra.balance.AddMoneyActivity
import com.example.fundra.databinding.ActivityPaymentMethodsBinding
import com.example.fundra.databinding.ActivitySortingBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Payment_Methods_Activity : BottomSheetDialogFragment() {

        private var _binding: ActivityPaymentMethodsBinding? = null
        private val binding get() = _binding!!


        override fun onCreateView(
                inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?
        ): View {
                _binding = ActivityPaymentMethodsBinding.inflate(inflater, container, false)

                binding.cardArrow.setOnClickListener{
                        startActivity(Intent(requireActivity(), AddMoneyActivity::class.java))
                }
                binding.vodafoneArrow.setOnClickListener{
                        startActivity(Intent(requireActivity(), vodafone_Activity_receipt::class.java))
                }
                binding.instaArrow.setOnClickListener{
                        startActivity(Intent(requireActivity(), InstaPayActivity::class.java))
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