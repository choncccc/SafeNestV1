package com.example.safenest

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddWeight.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddWeight : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var age: EditText
    private lateinit var saveButton: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_add_weight, container, false)

            weightEditText = view.findViewById(R.id.WeightTracker_Weight)
            heightEditText = view.findViewById(R.id.WeightTracker_Height)
            age = view.findViewById(R.id.WeightTracker_Age)
            saveButton = view.findViewById(R.id.save)
            dbHelper = DatabaseHelper(requireContext())

            saveButton.setOnClickListener {
                val weight = weightEditText.text.toString().toInt()
                val height = heightEditText.text.toString().toInt()
                val age = age.text.toString().toInt()
                val dateNow: LocalDate = now()
                val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
                val formattedDate: String = dateNow.format(formatter)

                // Handle the save action (e.g., save the weight to a database or shared preferences)
                saveWeight(age, weight, height, formattedDate)
            }

            return view
        }

    fun saveWeight(age: Int, weight: Int, height: Int, inputDate: String) {
        if (dbHelper.fetchUserId()?.let { dbHelper.getBiometrics(it).isEmpty() } == true) {
            dbHelper.insertBiometrics(age, weight, height, dbHelper.fetchUserId())
            dbHelper.insertWeight(inputDate, weight, dbHelper.fetchUserId())
        } else {
            dbHelper.insertWeight(inputDate, weight, dbHelper.fetchUserId())
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddWeight.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddWeight().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}