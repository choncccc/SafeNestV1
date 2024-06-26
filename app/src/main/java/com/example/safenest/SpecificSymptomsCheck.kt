package com.example.safenest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.safenest.databinding.FragmentSpecificSymptomsCheckBinding

class SpecificSymptomsCheck : Fragment() {
    private var _binding: FragmentSpecificSymptomsCheckBinding? = null
    private val binding get() = _binding!!
    private lateinit var txtBox: EditText
    private lateinit var results: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSpecificSymptomsCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtBox = binding.txtBox
        results = binding.TVResults

        binding.perTrimester.setOnClickListener {
            updateFragment(SymptomsChecker())
        }

        binding.perSpecificSymp.setOnClickListener {
            checkSymptoms()
        }

        txtBox.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (txtBox.right - txtBox.compoundDrawables[2].bounds.width())) {
                    checkSymptoms()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkSymptoms() {
        val userInput = txtBox.text.toString().trim()
        val symptomsMap = mapOf(
            "Fatigue" to getString(R.string.relief_fatigue),
            "Nausea" to getString(R.string.relief_nausea),
            "Headaches" to getString(R.string.relief_headaches),
            "Constipation" to getString(R.string.relief_constipation),
            "Breast tenderness" to getString(R.string.relief_breast_tenderness),
            "Heartburn" to getString(R.string.relief_heartburn),
            "Backache" to getString(R.string.relief_backache),
            "Frequent urination" to getString(R.string.relief_frequent_urination),
            "Bloating" to getString(R.string.relief_bloating),
            "Vaginal discharge" to getString(R.string.relief_vaginal_discharge),
            "Breast changes" to getString(R.string.relief_breast_changes),
            "Food aversions" to getString(R.string.relief_food_aversions),
            "Hemorrhoids" to getString(R.string.relief_hemorrhoids),
            "Food craving" to getString(R.string.relief_food_craving),
            "Missed period" to getString(R.string.relief_missed_period),
            "Mood swings" to getString(R.string.relief_mood_swings),
            "Cramp" to getString(R.string.relief_cramp),
            "Dizziness" to getString(R.string.relief_dizziness),
            "Shortness of breath" to getString(R.string.relief_shortness_of_breath),
            "Skin changes" to getString(R.string.relief_skin_changes),
            "Varicose veins" to getString(R.string.relief_varicose_veins),
            "Bleeding gums" to getString(R.string.relief_bleeding_gums),
            "Nosebleed" to getString(R.string.relief_nosebleed),
            "Nasal congestion" to getString(R.string.relief_nasal_congestion)
        )

        val foundSymptoms = symptomsMap.filterKeys { userInput.contains(it, ignoreCase = true) }

        if (foundSymptoms.isNotEmpty()) {
            results.text = foundSymptoms.values.joinToString("\n\n")
        } else {
            results.text = "No symptoms found."
        }
    }

    private fun updateFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
