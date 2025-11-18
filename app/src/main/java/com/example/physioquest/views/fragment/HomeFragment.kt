package com.example.physioquest.views.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView // Keep for no_activity_message
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.physioquest.R
import com.example.physioquest.adapters.RecentActivityAdapter // Keep
import com.example.physioquest.data.database.AppRepository // Keep
import com.example.physioquest.data.results.RecentActivityItem // Keep
import com.example.physioquest.data.results.WorkoutResult // Keep
import com.example.physioquest.util.MemoryManagement
import com.example.physioquest.util.MyApplication
import com.example.physioquest.util.MyUtils // Keep if used in RecentActivityAdapter logic
import com.example.physioquest.viewmodels.ResultViewModel // Keep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(), MemoryManagement {
    @Suppress("PropertyName")
    val TAG = "RepDetect Home Fragment"

    // ❌ ลบตัวแปรที่เกี่ยวข้องกับ Progress Bar และ Plans ออก
    // private lateinit var homeViewModel: HomeViewModel
    // private lateinit var progressText: TextView
    // private lateinit var recyclerView: RecyclerView // เดิมคือ today_plans
    // private lateinit var noPlanTV: TextView
    // private lateinit var progressBar: ProgressBar
    // private lateinit var progressPercentage: TextView
    // private lateinit var addPlanViewModel: AddPlanViewModel
    // private lateinit var adapter: PlanAdapter
    // private var planList: List<Plan>? = emptyList()
    // private var notCompletePlanList: MutableList<Plan>? = Collections.emptyList()
    // private var workoutResults: List<WorkoutResult>? = null // ใช้แค่ใน fun loadRecentActivityData() ก็พอ

    // ✅ ตัวแปรที่เกี่ยวข้องกับ Recent Activity (ถูกคงไว้)
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var recentActivityRecyclerView: RecyclerView
    private lateinit var recentActivityAdapter: RecentActivityAdapter
    private lateinit var appRepository: AppRepository

    // ตัวแปรสำหรับ Message (ต้องใช้ TextView)
    private lateinit var noActivityMessage: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Initialization for Recent Activity (KEEP)

        // ❌ ลบ: progressText = view.findViewById(R.id.exercise_left)
        // ❌ ลบ: recyclerView = view.findViewById(R.id.today_plans)
        // ❌ ลบ: noPlanTV = view.findViewById(R.id.no_plan)
        // ❌ ลบ: progressBar = view.findViewById(R.id.progress_bar)
        // ❌ ลบ: progressPercentage = view.findViewById(R.id.progress_text)

        // ✅ คงไว้สำหรับการทำงานของ Recent Activity
        recentActivityRecyclerView = view.findViewById(R.id.recentActivityRecyclerView)
        // 🚩 NEW: ต้องหา ID ของข้อความ "No activity message"
        noActivityMessage = view.findViewById(R.id.no_activity_message)

        recentActivityAdapter = RecentActivityAdapter(emptyList())
        recentActivityRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        recentActivityRecyclerView.adapter = recentActivityAdapter

        appRepository = AppRepository(requireActivity().application)

        // Initialize ViewModel
        resultViewModel = ResultViewModel(MyApplication.getInstance())

        // 2. Load Recent Activity Data (KEEP Logic)
        loadRecentActivityData(view) // 🚩 ส่ง view เข้าไป

        // 3. Remove all obsolete logic calls related to progress bar and today's plans.
        // ❌ ลบ Logic การเรียกใช้ homeViewModel และ AddPlanViewModel ทั้งหมด

    }

    private fun loadRecentActivityData(view: View) {
        // Observer or loading logic for recent activities (KEPT and ADAPTED)
        lifecycleScope.launch {
            // Fetch workout results asynchronously
            val workoutResults = resultViewModel.getRecentWorkout()

            // Transform WorkoutResult objects into RecentActivityItem objects
            // 💡 Note: MyUtils.exerciseNameToDisplay is needed here
            val imageResources = arrayOf(R.drawable.blue, R.drawable.green, R.drawable.orange)

            val recentActivityItems = workoutResults?.mapIndexed { index, it ->
                RecentActivityItem(
                    imageResId = imageResources[index % imageResources.size],
                    exerciseType = MyUtils.exerciseNameToDisplay(it.exerciseName),
                    reps = "${it.repeatedCount} reps"
                )
            }

            // Update the adapter with the transformed data
            withContext(Dispatchers.Main) {
                val data = recentActivityItems ?: emptyList()
                recentActivityAdapter.updateData(data)

                // Check if the recentActivityItems list is empty
                if (data.isEmpty()) {
                    recentActivityRecyclerView.isVisible = false
                    noActivityMessage.isVisible = true
                } else {
                    recentActivityRecyclerView.isVisible = true
                    noActivityMessage.isVisible = false
                }
            }
        }
    }

    // ❌ ลบฟังก์ชันที่ไม่ใช้ทั้งหมด (updateResultFromDatabase, loadDataAndSetupChart, updateProgressViews, ฯลฯ)

    override fun clearMemory() {
        // ❌ ลบ: planList = null, notCompletePlanList = null
        // workoutResults = null // ลบถ้าไม่ใช้เป็น Member Variable
    }

    override fun onDestroy() {
        clearMemory()
        super.onDestroy()
    }

    // ❌ ลบ onItemClicked(planId: Int, position: Int) และ updateEmptyPlan(...)
}