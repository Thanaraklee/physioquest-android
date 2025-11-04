package com.example.physioquest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.physioquest.R
import com.example.physioquest.data.PainArea

class SelectedPainAreaAdapter(
    private val items: MutableList<PainArea>,
    private val onRemoveClick: (PainArea) -> Unit
) : RecyclerView.Adapter<SelectedPainAreaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // อ้างอิงถึงปุ่มใน list_item_pain_area.xml
        val button: Button = view.findViewById(R.id.btn_pain_area)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // ใช้ Layout ของรายการด้านล่าง
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pain_area, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // 1. กำหนดข้อความและสี
        holder.button.text = item.name
        holder.button.setBackgroundResource(R.drawable.rounded_pain_area_bg)
        holder.button.setTextColor(holder.itemView.context.resources.getColor(android.R.color.black))

        // 2. กำหนดไอคอน
        // 🚩 แก้ไข: แยกการกำหนดไอคอน (ซ้าย: Icon ร่างกาย, ขวา: Icon ปิด)
        holder.button.setCompoundDrawablesRelativeWithIntrinsicBounds(
            item.iconResId, // ไอคอนด้านซ้าย (Icon ร่างกาย)
            0,
            0, //
            0
        )

        // 3. กำหนด Margin/Padding (เพื่อให้รายการด้านบนไม่ชิดกันเกินไป)
        // 🚩 แก้ไข: ต้องตรวจสอบประเภทของ LayoutParams ก่อน Cast
        val layoutParams = holder.itemView.layoutParams
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            val marginEnd = holder.itemView.context.resources.getDimensionPixelSize(R.dimen.spacing_small)
            layoutParams.marginEnd = marginEnd
            holder.itemView.layoutParams = layoutParams
        } else {
            // ถ้าไม่ใช่ MarginLayoutParams ให้สร้างใหม่ (กรณี RecyclerView สร้าง LayoutParams แบบพื้นฐาน)
            val newLayoutParams = ViewGroup.MarginLayoutParams(layoutParams)
            newLayoutParams.marginEnd = holder.itemView.context.resources.getDimensionPixelSize(R.dimen.spacing_small)
            holder.itemView.layoutParams = newLayoutParams
        }

        // 4. Listener (เมื่อคลิกปุ่มด้านบน คือการ "นำออก")
        holder.button.setOnClickListener {
            onRemoveClick(item)
        }
    }

    override fun getItemCount() = items.size
}