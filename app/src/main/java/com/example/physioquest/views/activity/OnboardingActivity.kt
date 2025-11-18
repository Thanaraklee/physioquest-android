package com.example.physioquest.views.activity

import com.example.physioquest.onboarding.OnboardingPagerAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.physioquest.onboarding.FirstOnboardingFragment
import com.example.physioquest.R
import com.example.physioquest.onboarding.SecondOnboardingFragment
import com.example.physioquest.onboarding.ThirdOnboardingFragment
import com.example.physioquest.util.MemoryManagement
import com.example.physioquest.views.activity.authenpage.LoginActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

/**
 * Activity for handling onboarding screens and navigation.
 */
class OnboardingActivity : AppCompatActivity(), MemoryManagement {
    private lateinit var viewPager: ViewPager2
    private lateinit var prefManager: PrefManager
    private lateinit var fragments: List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Find and initialize UI components
        val nextButton = findViewById<Button>(R.id.nextButton)
        prefManager = PrefManager(this)
        // List of onboarding fragments
        fragments = listOf(
            FirstOnboardingFragment(),
            SecondOnboardingFragment(),
            ThirdOnboardingFragment()
        )
        viewPager = findViewById(R.id.viewPager)

        // Set up the adapter for the ViewPager
        val onboardingAdapter = OnboardingPagerAdapter(this,fragments)
        viewPager.adapter = onboardingAdapter

        // Initialize and attach the DotsIndicator
        val indicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        indicator.attachTo(viewPager)

        nextButton.setOnClickListener {

            // 1. รับ Fragment ที่กำลังแสดงผลอยู่ปัจจุบัน
            val currentFragment = fragments[viewPager.currentItem]
            var shouldProceed = true

            // 2. ตรวจสอบเฉพาะ FirstOnboardingFragment (หน้าแรก)
            if (currentFragment is FirstOnboardingFragment) {
                // เรียกใช้ฟังก์ชัน Validation
                shouldProceed = currentFragment.validateSelectionAndNotifyActivity()
            }

            // 3. ดำเนินการต่อเมื่อ Validation ผ่าน
            if (shouldProceed) {
                if (viewPager.currentItem < fragments.size - 1) {
                    viewPager.currentItem++
                } else {
                    // Set the flag to indicate onboarding completion
                    prefManager.setFirstTimeLaunch(false)

                    // Navigate to LoginActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            // 🚩 ลบ Logic การอัปเดตข้อความปุ่มออกจากที่นี่ เนื่องจากได้ย้ายไปอยู่ใน OnPageChangeCallback แล้ว
        }
    }

    override fun clearMemory() {
        // Clear all references in this class
        viewPager.adapter = null
    }

    override fun onDestroy() {
        clearMemory()
        super.onDestroy()
    }

}

/**
 * Helper class to manage shared preferences for onboarding.
 */
class PrefManager(context: Context) {
    private val pref: SharedPreferences =
        context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    //Check if it is the first time the app is being launched.
    fun isFirstTimeLaunch(): Boolean {
        return pref.getBoolean("isFirstTimeLaunch", true)
    }

    //Set the first-time launch flag.
    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        pref.edit().putBoolean("isFirstTimeLaunch", isFirstTime).apply()
    }
}