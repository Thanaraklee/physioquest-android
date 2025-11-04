package com.example.physioquest.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private lateinit var rvSelectedPainArea: RecyclerView // รายการที่เลือกแล้วด้านบน (My Pain Point List)
    private lateinit var tvMyPainPoint: TextView // TextView "None"

    // 2. การประกาศรายการข้อมูล
    private val allPainAreas = mutableListOf(
        // ข้อมูลตัวอย่าง Pain Area พร้อม Icon Resource ID ที่สมมติ
        PainArea(1, "Neck", R.drawable.ic_neck),
        PainArea(2, "Shoulder", R.drawable.ic_shoulder),
        PainArea(3, "Upper Back", R.drawable.ic_upper_back),
        PainArea(4, "Lower Back", R.drawable.ic_lower_back),
        PainArea(5, "Wrist", R.drawable.ic_wrist)
    )

    private val selectedPainAreas = mutableListOf<PainArea>() // รายการที่ถูกเลือกแล้ว

    // 3. การประกาศ Adapters
    private lateinit var adapter: PainAreaAdapter // Adapter สำหรับรายการด้านล่าง
    private lateinit var selectedAdapter: SelectedPainAreaAdapter // Adapter สำหรับรายการด้านบน

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // อ้างอิง Layout (first_fragment_onboarding.xml ที่ถูกแทนที่ด้วย Pain Point Selector Layout)
        val view = inflater.inflate(R.layout.first_fragment_onboarding, container, false)

        // 4. การเชื่อมโยง View Components
        rvPainArea = view.findViewById(R.id.rv_pain_area)
        rvSelectedPainArea = view.findViewById(R.id.rv_selected_pain_area)
        tvMyPainPoint = view.findViewById(R.id.tv_my_pain_point)


        // 5. Setup
        setupRecyclerView()
        updateMyPainPointDisplay()





        return view
    }

    private fun setupRecyclerView() {
        // A. Setup สำหรับรายการด้านล่าง (Pain Area)
        adapter = PainAreaAdapter(allPainAreas) { clickedArea ->
            handlePainAreaClick(clickedArea)
        }
        rvPainArea.layoutManager = LinearLayoutManager(requireContext())
        rvPainArea.adapter = adapter

        // B. Setup สำหรับรายการที่เลือกแล้วด้านบน (My Pain Point)
        selectedAdapter = SelectedPainAreaAdapter(selectedPainAreas) { removedArea ->
            handlePainAreaClick(removedArea) // ใช้ฟังก์ชันเดิม เพื่อยกเลิกการเลือก
        }

        // 💡 เลือก: RecyclerView.VERTICAL หากต้องการปุ่มแบบเต็มความกว้างเหมือนด้านล่าง
        // หรือ RecyclerView.HORIZONTAL หากต้องการเรียงเป็นแถวแนวนอนเหมือน Tag
        rvSelectedPainArea.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvSelectedPainArea.adapter = selectedAdapter
    }

    private fun handlePainAreaClick(clickedArea: PainArea) {
        if (clickedArea.isSelected) {
            // ยกเลิกการเลือก: ลบออกจาก selectedPainAreas
            clickedArea.isSelected = false
            selectedPainAreas.remove(clickedArea)
        } else {
            // เลือก: เพิ่มเข้าใน selectedPainAreas
            clickedArea.isSelected = true
            selectedPainAreas.add(clickedArea)
        }

        // อัปเดต RecyclerView ทั้งสองส่วน
        adapter.notifyDataSetChanged()      // อัปเดตรายการด้านล่าง (ซ่อน/แสดง)
        selectedAdapter.notifyDataSetChanged() // อัปเดตรายการด้านบน (เพิ่ม/ลด)

        updateMyPainPointDisplay() // อัปเดตสถานะ None/Continue
    }

    // อัปเดตการแสดงผล Pain Point ที่เลือกแล้วด้านบน
    private fun updateMyPainPointDisplay() {
        if (selectedPainAreas.isEmpty()) {
            // ถ้าไม่มีการเลือก: แสดง TextView "None"
            tvMyPainPoint.visibility = View.VISIBLE
            rvSelectedPainArea.visibility = View.GONE
        } else {
            // ถ้ามีการเลือก: แสดง RecyclerView
            tvMyPainPoint.visibility = View.GONE
            rvSelectedPainArea.visibility = View.VISIBLE
        }
    }

}