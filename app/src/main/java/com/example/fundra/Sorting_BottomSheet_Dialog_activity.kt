package com.example.fundra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fundra.databinding.ActivitySortingBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
class Sorting_BottomSheet_Dialog_activity : BottomSheetDialogFragment() {

    private var _binding: ActivitySortingBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    var filterListener: DonationActivity.OnFilterSelectedListener? = null
    var deleteVisibilityListener: DeleteVisibilityListener? = null

    private var selectedType: String = "All"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ActivitySortingBottomSheetDialogBinding.inflate(inflater, container, false)

        binding.delete.setOnClickListener {
            dismiss()
        }

        binding.food.setOnClickListener {
            selectedType = "Food / Community development"
        }

        binding.AlZakah.setOnClickListener {
            selectedType = "Al Zakah / Al Sadakat"
        }

        binding.Hospitals.setOnClickListener {
            selectedType = "Hospitals / Medical Aids"
        }

        binding.selectAll.setOnClickListener {
            selectedType = "All"
        }
        binding.resultsBtn.setOnClickListener {
            filterListener?.onFilterSelected(selectedType)
            deleteVisibilityListener?.onDeleteVisibilityChanged(true)
            dismiss()
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    interface DeleteVisibilityListener {
        fun onDeleteVisibilityChanged(visible: Boolean)
    }
    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = (resources.displayMetrics.heightPixels * 0.5).toInt()
    }
}
