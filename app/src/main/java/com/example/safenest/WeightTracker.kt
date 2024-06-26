package com.example.safenest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.safenest.databinding.FragmentWeightTrackerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class WeightTracker : Fragment() {

    private lateinit var binding: FragmentWeightTrackerBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var header: TextView
    private lateinit var maincontainer: LinearLayout
    private lateinit var dbHelper:DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

       binding= FragmentWeightTrackerBinding.inflate(inflater, container, false)
        dbHelper = DatabaseHelper(requireContext())
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = view.findViewById<View>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight = 85
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        header = view.findViewById(R.id.header)
        maincontainer = view.findViewById(R.id.maincontainer)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // TODO
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                updateCardViewsAlpha(slideOffset)
            }
        })

        Log.e("USER ID", dbHelper.fetchUserId().toString())

        if (!dbHelper.fetchUserId()?.let { dbHelper.getBiometrics(it).isEmpty() }!!) {
            displayData(view)
        }

        binding.btnAddValues.setOnClickListener{
            updateFragment(AddWeight())
        }
    }
    private fun updateCardViewsAlpha(slideOffset: Float) {
        val alpha = slideOffset
        applyAlphaAnimation(header, alpha)
        applyAlphaAnimation(maincontainer, alpha)


    }

    private fun displayData(view: View) {
        val bloodPressureData =
            dbHelper.fetchUserId()?.let { dbHelper.getWeight(it) } // Fetch the data
        val tableLayout = view.findViewById<TableLayout>(R.id.table)

        // Clear existing rows
        tableLayout.removeAllViews()

        // Add table headers
        val tableHeader = TableRow(requireContext())
        val headerDate = TextView(requireContext())
        val headerBloodPressure = TextView(requireContext())
        val headerDifference = TextView(requireContext())

        headerDate.text = "Date"
        headerBloodPressure.text = "Blood Pressure"
        headerDifference.text = "Difference"

        // Set background color and padding
        headerDate.setBackgroundColor(Color.WHITE)
        headerBloodPressure.setBackgroundColor(Color.WHITE)
        headerDifference.setBackgroundColor(Color.WHITE)

        val padding = 16
        headerDate.setPadding(padding, padding, padding, padding)
        headerBloodPressure.setPadding(padding, padding, padding, padding)
        headerDifference.setPadding(padding, padding, padding, padding)

        tableHeader.addView(headerDate)
        tableHeader.addView(headerBloodPressure)
        tableHeader.addView(headerDifference)

        tableLayout.addView(tableHeader)

        // Add rows dynamically
        if (bloodPressureData != null) {
            for (record in bloodPressureData) {
                val tableRow = TableRow(requireContext())
                val dateTextView = TextView(requireContext())
                val bloodPressureTextView = TextView(requireContext())
                val differenceTextView = TextView(requireContext())
                val change = TextView(requireContext())

                dateTextView.text = record.date
                bloodPressureTextView.text = record.week.toString()
                differenceTextView.text = record.weight.toString()
                change.text = record.difference.toString()

                // Set background color and padding
                dateTextView.setBackgroundColor(Color.WHITE)
                bloodPressureTextView.setBackgroundColor(Color.WHITE)
                differenceTextView.setBackgroundColor(Color.WHITE)
                change.setBackgroundColor(Color.WHITE)

                dateTextView.setPadding(padding, padding, padding, padding)
                bloodPressureTextView.setPadding(padding, padding, padding, padding)
                differenceTextView.setPadding(padding, padding, padding, padding)
                change.setPadding(padding, padding, padding, padding)

                tableRow.addView(dateTextView)
                tableRow.addView(bloodPressureTextView)
                tableRow.addView(differenceTextView)
                tableRow.addView(change)

                tableLayout.addView(tableRow)
            }
        }
    }

    private fun applyAlphaAnimation(view: View, alpha: Float) {
        val animation = AlphaAnimation(view.alpha, alpha)
        animation.duration = 200 // Set the duration of the fade animation
        animation.interpolator = DecelerateInterpolator()
        animation.fillAfter = true
        view.startAnimation(animation)
        view.alpha = alpha
    }
    private fun updateFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}