package com.example.fundra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fundra.databinding.ActivityPrivacyPolicesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Privacy_Polices_Activity : BottomSheetDialogFragment() {

    private var _binding: ActivityPrivacyPolicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityPrivacyPolicesBinding.inflate(inflater, container, false)
        binding.accept.setOnClickListener {
            dismiss()
        }
        binding.reject.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
