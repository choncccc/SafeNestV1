package com.example.safenest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.safenest.databinding.FragmentBmiCalulatorBinding
import kotlin.math.pow

class bmiCalulator : Fragment() {
    private lateinit var binding: FragmentBmiCalulatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBmiCalulatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalculate.setOnClickListener {
            val weightText = binding.EnterWeight.text.toString()
            val heightText = binding.EnterHeight.text.toString()

            if (weightText.isNotEmpty() && heightText.isNotEmpty()) {
                try {
                    val weight = weightText.toDouble()
                    val height = heightText.toDouble() / 100
                    val bmi = calculateBMI(weight, height)
                    val classification = classifyBMI(bmi)

                    binding.setTextBMI.text = String.format("%.2f", bmi)
                    binding.setTextBMIClassi.text = classification
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Invalid input. Please enter numbers only.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter both weight and height", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun calculateBMI(weight: Double, height: Double): Double {
        return weight / height.pow(2)
    }

    private fun classifyBMI(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi in 18.5..24.9 -> "Normal weight"
            bmi in 25.0..29.9 -> "Overweight"
            else -> "Obese"
        }
    }
}
