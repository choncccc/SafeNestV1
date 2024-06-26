package com.example.safenest

import PulseRateData
import RespiratoryRateData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safenest.databinding.FragmentHealthMonitoringDashboardContentsBinding


class HealthMonitoringDashboardContents : Fragment() {

    private lateinit var binding: FragmentHealthMonitoringDashboardContentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthMonitoringDashboardContentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.BloodPressureCardData.setOnClickListener{
            updateFragment(BloodPresssureData())
        }
        binding.BloodSugarCardData.setOnClickListener{
            updateFragment(BloodSugarLevelData())
        }
        binding.BodyTempCardData.setOnClickListener{
            updateFragment(BodyTemperatureData())
        }
        binding.PulseRateCardData.setOnClickListener{
            updateFragment(PulseRateData())
        }
        binding.RespiratoryRateData.setOnClickListener{
            updateFragment(RespiratoryRateData())
        }
        binding.OxygenSaturationData.setOnClickListener{
            updateFragment(OxygenSaturationData())
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
