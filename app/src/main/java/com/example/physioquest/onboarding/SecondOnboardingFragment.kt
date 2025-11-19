package com.example.physioquest.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.physioquest.R

class SecondOnboardingFragment : Fragment() {

    private lateinit var mainLayout: ConstraintLayout
    private lateinit var painLevelSlider: SeekBar
    private lateinit var painFaceIcon: ImageView
    private lateinit var tvPainDescription: TextView

    private var selectedPainLevel: Int = 0

    private lateinit var painColors: List<Int>

    private val painDescriptions = arrayOf(
        "No pain",
        "Hardly notice pain",
        "Notice pain, does not interfere with activities ",
        "Sometimes distract me",
        "Distracts me, can do usual activities",
        "Interrupts some activities",
        "Hard to ignore, avoid usual activities",
        "Focus of attention, prevents doing daily activities",
        "Awful, hard to do anything",
        "Can't bear the pain, unable to do anything",
        "As bad as it could be, nothing else matters"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.second_fragment_onboarding, container, false)

        // 2. เตรียมชุดสี
        painColors = resources.getIntArray(R.array.pain_colors).asList()

        mainLayout = view.findViewById(R.id.main_content_layout)
        painLevelSlider = view.findViewById(R.id.pain_level_slider)
        painFaceIcon = view.findViewById(R.id.pain_face_icon)
        tvPainDescription = view.findViewById(R.id.tv_pain_description)

        // ตั้งค่าเริ่มต้น
        updatePainLevelUI(painLevelSlider.progress)

        // 3. ตั้งค่า Listener สำหรับ SeekBar
        painLevelSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updatePainLevelUI(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        return view
    }

    private fun updatePainLevelUI(level: Int) {
        selectedPainLevel = level

        val whiteColorStateList = ContextCompat.getColorStateList(requireContext(), android.R.color.white)
        painLevelSlider.thumbTintList = whiteColorStateList

        //  เปลี่ยนข้อความคำอธิบาย
        tvPainDescription.text = painDescriptions.getOrElse(level) { painDescriptions[0] }

        //  เปลี่ยนไอคอน (ตามระดับความเจ็บปวด)
        val iconRes = when {
            level <= 2 -> R.drawable.ic_pain_happy
            level <= 5 -> R.drawable.ic_pain_neutral
            else -> R.drawable.ic_pain_sad
        }
        painFaceIcon.setImageResource(iconRes)
    }





}