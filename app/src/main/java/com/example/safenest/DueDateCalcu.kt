package com.example.safenest

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safenest.databinding.FragmentDueDateCalcuBinding
import java.text.SimpleDateFormat
import java.util.*

class DueDateCalcu : Fragment() {
    private lateinit var binding: FragmentDueDateCalcuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDueDateCalcuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalculate.setOnClickListener {
            val inputDate = binding.EnterDueDate.text.toString()
            if (inputDate.isNotEmpty()) {
                try {
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                    sdf.isLenient = false // Set to strict parsing
                    val lastMenstrualDate = sdf.parse(inputDate)
                    val lastMenstrualCalendar = Calendar.getInstance().apply { time = lastMenstrualDate }
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                    if (lastMenstrualCalendar.get(Calendar.YEAR) >= 2024 && lastMenstrualCalendar.get(Calendar.YEAR) <= currentYear) {
                        val dueDate = calculateDueDate(lastMenstrualDate!!)
                        binding.setTextDueDate.text = sdf.format(dueDate)
                    } else {
                        Toast.makeText(requireContext(), "This tool only works up to 40 weeks of pregnancy. Please enter a date within the last 40 weeks.", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Invalid date format. Please use dd/MM/yyyy", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter the first day of the period", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun calculateDueDate(lastMenstrualDate: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = lastMenstrualDate
        calendar.add(Calendar.DAY_OF_YEAR, 280) // Adding 280 days for a typical pregnancy duration
        return calendar.time
    }
}
