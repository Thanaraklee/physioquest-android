package com.example.physioquest.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.physioquest.R

class ThirdOnboardingFragment : Fragment() {

    private lateinit var buttons: List<Button>
    private var selectedFrequency: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.third_fragment_onboarding, container, false)

        val btn1_3 = view.findViewById<Button>(R.id.btn_freq_1_3)
        val btn3_5 = view.findViewById<Button>(R.id.btn_freq_3_5)
        val btn5_7 = view.findViewById<Button>(R.id.btn_freq_5_7)

        buttons = listOf(btn1_3, btn3_5, btn5_7)

        setupSelectionLogic()

        return view
    }


    private fun setupSelectionLogic() {
        buttons.forEach { button ->
            button.isClickable = true
            button.isFocusable = true

            button.isActivated = false

            button.setOnClickListener {
                handleButtonClick(it as Button)
            }
        }
    }

    private fun handleButtonClick(clickedButton: Button) {
        buttons.forEach { btn ->
            if (btn != clickedButton) {
                btn.isActivated = false
            }
        }

        // 2. สลับสถานะของปุ่มที่ถูกคลิก
        clickedButton.isActivated = !clickedButton.isActivated

        // 3. จัดการข้อมูลที่ถูกเลือก
        if (clickedButton.isActivated) {
            selectedFrequency = clickedButton.text.toString()
        } else {
            selectedFrequency = null
        }
    }


}