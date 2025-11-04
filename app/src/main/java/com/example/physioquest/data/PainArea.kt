package com.example.physioquest.data

// Data class สำหรับเก็บข้อมูลจุดที่เจ็บ
data class PainArea(
    val id: Int,
    val name: String,
    val iconResId: Int,
    var isSelected: Boolean = false
)