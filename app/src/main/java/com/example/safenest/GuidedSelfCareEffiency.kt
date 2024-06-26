package com.example.safenest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safenest.databinding.FragmentGuidedSelfCareEffiencyBinding
import com.example.safenest.databinding.FragmentHealthMonitoringDashboardContentsBinding

class GuidedSelfCareEffiency : Fragment() {
    private lateinit var binding : FragmentGuidedSelfCareEffiencyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGuidedSelfCareEffiencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.AppointmentReminderData.setOnClickListener{
            updateFragment(AppointmentReminder())

        }
        binding.ToDoData.setOnClickListener {
            updateFragment(ToDo())
        }
        binding.PersonalizedNotif.setOnClickListener {
            updateFragment(PersonalizedNotif())
        }
        binding.MedicationTracker.setOnClickListener {
            updateFragment(MedicationTracker())
        }
        binding.WeightTracker.setOnClickListener {
            updateFragment(WeightTracker())
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