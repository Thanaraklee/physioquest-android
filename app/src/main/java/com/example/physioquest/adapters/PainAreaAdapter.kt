package com.example.physioquest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.physioquest.R
import com.example.physioquest.data.PainArea

class PainAreaAdapter(
    private val items: List<PainArea>,
    private val onClick: (PainArea) -> Unit
) : RecyclerView.Adapter<PainAreaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.btn_pain_area)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pain_area, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        if (item.isSelected) {
            // ถ้าถูกเลือกแล้ว: ซ่อนรายการจากด้านล่าง
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        } else {
            // ถ้ายังไม่ถูกเลือก: แสดงรายการพร้อมไอคอน
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            holder.button.text = item.name

            // 🚩 เพิ่มโค้ดส่วนนี้เพื่อกำหนดไอคอนให้กับปุ่มด้านล่าง
            holder.button.setCompoundDrawablesRelativeWithIntrinsicBounds(
                item.iconResId, // ไอคอนด้านซ้าย (Icon ร่างกาย)
                0,
                0, // ไม่ต้องมีไอคอนด้านขวาสำหรับปุ่มด้านล่าง
                0
            )

            // ตั้งค่าพื้นหลัง/สีเริ่มต้น (ถ้ามี)
            holder.button.setBackgroundResource(R.drawable.rounded_pain_area_bg)
            holder.button.setTextColor(holder.itemView.context.resources.getColor(android.R.color.black))

            holder.button.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount() = items.size
}