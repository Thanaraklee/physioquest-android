package com.example.physioquest.views.fragment

import com.example.physioquest.R
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.physioquest.data.plan.Plan
import com.example.physioquest.util.MemoryManagement
import com.example.physioquest.viewmodels.AddPlanViewModel
import kotlinx.coroutines.launch

/**
 * PlanStepTwoFragment: Fragment for capturing additional details of an exercise plan.
 *
 * This fragment allows the user to input details such as the exercise name, repetition count,
 * and select the days of the week for the exercise plan.
 */
class PlanStepTwoFragment : Fragment(), MemoryManagement {
    private lateinit var addPlanViewModel: AddPlanViewModel
    private var mExerciseName: String = ""
    private var mKcal: Double = 0.0
    private val selectedDays = mutableListOf<String>()
    private lateinit var days: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plan_step_two, container, false)
        // Set the values
        mExerciseName = arguments?.getString("exerciseName") ?: ""
        mKcal = arguments?.getDouble("caloriesPerRep") ?: 0.0
        days = resources.getStringArray(R.array.days)
        return view
    }

    override fun clearMemory() {
        mExerciseName = ""
        mKcal = 0.0
        selectedDays.clear()
    }

    override fun onDestroy() {
        clearMemory()
        super.onDestroy()
    }
}