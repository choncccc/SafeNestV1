package com.example.safenest

import NutritionGuidanceContent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safenest.databinding.FragmentSelfAssessmentBinding


class FragmentSelfAssessment : Fragment() {

    private lateinit var binding: FragmentSelfAssessmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelfAssessmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.HealthMonitoringCard.setOnClickListener{
                updateFragment(HealthMonitoringDashboardContents())
            }
            binding.IntegratedHealthCard.setOnClickListener{
                updateFragment(IntegratedHealthRecordData())
            }
            binding.NutritionGuidanceCard.setOnClickListener{
                updateFragment(NutritionGuidanceContent())
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
