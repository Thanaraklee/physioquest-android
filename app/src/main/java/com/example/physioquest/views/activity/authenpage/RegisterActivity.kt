package com.example.physioquest.views.activity.authenpage

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.physioquest.R
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    // ประกาศตัวแปร (ตาม ID ใน XML)
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etDateOfBirth: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etEmail: EditText
    private lateinit var etMobileNo: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var checkboxTerms: CheckBox
    private lateinit var btnGetStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. เชื่อมโยง View
        initializeViews()

        // 2. กำหนด Listener
        etDateOfBirth.setOnClickListener {
            showDatePickerDialog()
        }

        btnGetStart.setOnClickListener {
            handleRegistration()
        }

        // 3. จัดการ Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_register_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initializeViews() {
        // เชื่อมโยงตัวแปรกับ ID ใน activity_register.xml
        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etDateOfBirth = findViewById(R.id.et_date_of_birth)
        rgGender = findViewById(R.id.rg_gender)
        etEmail = findViewById(R.id.et_email)
        etMobileNo = findViewById(R.id.et_mobile_no)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        checkboxTerms = findViewById(R.id.checkbox_terms)
        btnGetStart = findViewById(R.id.btn_get_start)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etDateOfBirth.setText(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun handleRegistration() {
        // ... (โค้ด Validation และ Logic การลงทะเบียน)

        val firstName = etFirstName.text.toString()
        val isTermsChecked = checkboxTerms.isChecked

        if (firstName.isEmpty() || etPassword.text.isEmpty()) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isTermsChecked) {
            Toast.makeText(this, "กรุณายอมรับข้อตกลง", Toast.LENGTH_SHORT).show()
            return
        }

        // ตัวอย่างสำเร็จ:
        Log.i("RegisterFlow", "Registration Successful for: $firstName")
        Toast.makeText(this, "ลงทะเบียนสำเร็จ!", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}