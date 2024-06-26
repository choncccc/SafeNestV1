package com.example.safenest

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.safenest.databinding.FragmentMedicationTrackerBinding

class MedicationTracker : Fragment() {


    private lateinit var binding: FragmentMedicationTrackerBinding
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMedicationTrackerBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper(requireContext())
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayData(view)
        binding.addSomething.setOnClickListener{
            updateFragment(MedicationTrackerAddMeds())
        }
    }

    private fun displayData(view: View) {
        val bloodPressureData =
            dbHelper.fetchUserId()?.let { dbHelper.getMedication(it) } // Fetch the data
        val tableLayout = view.findViewById<TableLayout>(R.id.table)

        // Clear existing rows
        tableLayout.removeAllViews()

        // Add table headers
        val tableHeader = TableRow(requireContext())
        val headerDate = TextView(requireContext())
        val headerBloodPressure = TextView(requireContext())
        val headerDifference = TextView(requireContext())
        val headertime = TextView(requireContext())
        val headerDosage = TextView(requireContext())

        headerDate.text = "Name"
        headerBloodPressure.text = "Frequency"
        headerDifference.text = "Schedule Mode"
        headertime.text = "Schedule Time"
        headerDosage.text = "Dosage"

        // Set background color and padding
        headerDate.setBackgroundColor(Color.WHITE)
        headerBloodPressure.setBackgroundColor(Color.WHITE)
        headerDifference.setBackgroundColor(Color.WHITE)
        headertime.setBackgroundColor(Color.WHITE)
        headerDosage.setBackgroundColor(Color.WHITE)


        val padding = 16
        headerDate.setPadding(padding, padding, padding, padding)
        headerBloodPressure.setPadding(padding, padding, padding, padding)
        headerDifference.setPadding(padding, padding, padding, padding)
        headertime.setPadding(padding, padding, padding, padding)
        headerDosage.setPadding(padding, padding, padding, padding)

        tableHeader.addView(headerDate)
        tableHeader.addView(headerBloodPressure)
        tableHeader.addView(headerDifference)
        tableHeader.addView(headertime)
        tableHeader.addView(headerDosage)

        tableLayout.addView(tableHeader)

        // Add rows dynamically
        if (bloodPressureData != null) {
            for (record in bloodPressureData) {
                val tableRow = TableRow(requireContext())
                val dateTextView = TextView(requireContext())
                val bloodPressureTextView = TextView(requireContext())
                val differenceTextView = TextView(requireContext())
                val schedTime = TextView(requireContext())
                val dosage = TextView(requireContext())

                dateTextView.text = record.name
                bloodPressureTextView.text = record.frequency.toString()
                differenceTextView.text = record.sched_mode.toString()
                schedTime.text = record.sched_time
                dosage.text = record.dosage.toString()

                // Set background color and padding
                dateTextView.setBackgroundColor(Color.WHITE)
                bloodPressureTextView.setBackgroundColor(Color.WHITE)
                differenceTextView.setBackgroundColor(Color.WHITE)
                schedTime.setBackgroundColor(Color.WHITE)
                dosage.setBackgroundColor(Color.WHITE)

                dateTextView.setPadding(padding, padding, padding, padding)
                bloodPressureTextView.setPadding(padding, padding, padding, padding)
                differenceTextView.setPadding(padding, padding, padding, padding)
                schedTime.setPadding(padding, padding, padding, padding)
                dosage.setPadding(padding, padding, padding, padding)

                tableRow.addView(dateTextView)
                tableRow.addView(bloodPressureTextView)
                tableRow.addView(differenceTextView)
                tableRow.addView(schedTime)
                tableRow.addView(dosage)

                tableLayout.addView(tableRow)
            }
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