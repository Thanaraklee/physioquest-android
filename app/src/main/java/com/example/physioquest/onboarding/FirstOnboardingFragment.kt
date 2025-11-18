package com.example.physioquest.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.physioquest.R
import com.example.physioquest.adapters.PainAreaAdapter
import com.example.physioquest.adapters.SelectedPainAreaAdapter
import com.example.physioquest.data.PainArea

class FirstOnboardingFragment : Fragment() {

    // 1. การประกาศตัวแปร UI
    private lateinit var rvPainArea: RecyclerView
    private lateinit var rvSelectedPainArea: RecyclerView
    private lateinit var tvMyPainPoint: TextView
    // 🚩 ลบ: private lateinit var btnContinue: Button ออก

    // 2. การประกาศรายการข้อมูล
    private val allPainAreas = mutableListOf(
        PainArea(1, "Neck", R.drawable.ic_neck),
        PainArea(2, "Shoulder", R.drawable.ic_shoulder),
        PainArea(3, "Upper Back", R.drawable.ic_upper_back),
        PainArea(4, "Lower Back", R.drawable.ic_lower_back),
        PainArea(5, "Wrist", R.drawable.ic_wrist)
    )

    private val selectedPainAreas = mutableListOf<PainArea>()

    // 3. การประกาศ Adapters
    private lateinit var adapter: PainAreaAdapter
    private lateinit var selectedAdapter: SelectedPainAreaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.first_fragment_onboarding, container, false)

        // 4. การเชื่อมโยง View Components
        rvPainArea = view.findViewById(R.id.rv_pain_area)
        rvSelectedPainArea = view.findViewById(R.id.rv_selected_pain_area)
        tvMyPainPoint = view.findViewById(R.id.tv_my_pain_point)
        // 🚩 ลบ: btnContinue = view.findViewById(R.id.btn_continue) ออก

        // 5. Setup
        setupRecyclerView()
        updateMyPainPointDisplay()

        // 🚩 ลบ: Logic จัดการปุ่ม Close และ Continue ออก

        return view
    }

    // ... (ฟังก์ชัน setupRecyclerView, handlePainAreaClick, updateMyPainPointDisplay ยังคงเดิม) ...

    private fun setupRecyclerView() {
        // A. Setup สำหรับรายการด้านล่าง (Pain Area)
        adapter = PainAreaAdapter(allPainAreas) { clickedArea ->
            handlePainAreaClick(clickedArea)
        }
        rvPainArea.layoutManager = LinearLayoutManager(requireContext())
        rvPainArea.adapter = adapter

        // B. Setup สำหรับรายการที่เลือกแล้วด้านบน (My Pain Point)
        selectedAdapter = SelectedPainAreaAdapter(selectedPainAreas) { removedArea ->
            handlePainAreaClick(removedArea)
        }
        rvSelectedPainArea.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvSelectedPainArea.adapter = selectedAdapter
    }

    private fun handlePainAreaClick(clickedArea: PainArea) {
        if (clickedArea.isSelected) {
            clickedArea.isSelected = false
            selectedPainAreas.remove(clickedArea)
        } else {
            clickedArea.isSelected = true
            selectedPainAreas.add(clickedArea)
        }

        adapter.notifyDataSetChanged()
        selectedAdapter.notifyDataSetChanged()
        updateMyPainPointDisplay()
    }

    private fun updateMyPainPointDisplay() {
        if (selectedPainAreas.isEmpty()) {
            tvMyPainPoint.visibility = View.VISIBLE
            rvSelectedPainArea.visibility = View.GONE
        } else {
            tvMyPainPoint.visibility = View.GONE
            rvSelectedPainArea.visibility = View.VISIBLE
        }
    }

    // 🚩 ฟังก์ชัน Validation ที่ OnboardingActivity เรียกใช้
    fun validateSelectionAndNotifyActivity(): Boolean {
        if (selectedPainAreas.isNotEmpty()) {
            return true
        } else {
            Toast.makeText(requireContext(), "Please select a pain area to continue.", Toast.LENGTH_SHORT).show()
            return false // ไม่ผ่าน Validation
        }
    }
}