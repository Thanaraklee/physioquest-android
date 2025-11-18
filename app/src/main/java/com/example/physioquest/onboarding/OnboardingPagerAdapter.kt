package com.example.physioquest.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Define the OnboardingPagerAdapter class, responsible for managing onboarding fragments
 */
class OnboardingPagerAdapter(
    fragmentActivity: FragmentActivity,
    // 🚩 FIX: เพิ่ม fragments: List<Fragment> ใน Constructor
    private val fragments: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    // 🚩 ลบรายการ fragments ที่สร้างซ้ำภายในคลาสออก

    // Return the number of fragments in the list
    override fun getItemCount(): Int = fragments.size

    // Create and return a fragment based on its position
    override fun createFragment(position: Int): Fragment {
        // 🚩 ใช้ fragments list ที่ถูกส่งเข้ามา
        return fragments[position]
    }
}