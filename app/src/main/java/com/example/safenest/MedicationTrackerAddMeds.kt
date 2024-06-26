package com.example.safenest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast


class MedicationTrackerAddMeds : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_medication_tracker_add_meds, container, false)
        val dbHelper = DatabaseHelper(requireContext())
        val spinner = view.findViewById<Spinner>(R.id.frequencySpinner)
        val toggleSwitch = view.findViewById<Switch>(R.id.toggleSwitch)
        val toggleLabel = view.findViewById<TextView>(R.id.toggleLabel)
        val medName = view.findViewById<EditText>(R.id.MedicationName)
        val save = view.findViewById<Button>(R.id.MedicationSaveToDB)
        var frequency = ""

        val frequencies = arrayOf(
            "Everyday",
            "Every X days",
            "Day of the Week",
            "Day of Month",
            "X day enable/ X day disable",
            "Any"
        )

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_items, frequencies)
        arrayAdapter.setDropDownViewResource(R.layout.dropdown_items) // Set the custom layout for dropdown items
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                // Handle spinner item selection here
                Toast.makeText(
                    requireContext(),
                    "Selected frequency is: ${frequencies[position]}",
                    Toast.LENGTH_LONG
                ).show()
                frequency = frequencies[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
        toggleLabel.text = if (toggleSwitch.isChecked) "ON" else "OFF"
        toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            toggleLabel.text = if (isChecked) "ON" else "OFF"
        }

        save.setOnClickListener {
            Toast.makeText(
                requireContext(),
                medName.text.toString(),
                Toast.LENGTH_LONG
            ).show()
            dbHelper.insertMedication(medName.text.toString(), "", "", frequency, 1, dbHelper.fetchUserId())
        }

        return view
    }



}