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
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.safenest.databinding.FragmentAppointmentReminderBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class AppointmentReminder : Fragment() {
    private lateinit var binding: FragmentAppointmentReminderBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var header: TextView
    private lateinit var maincontainer: FrameLayout
    private  lateinit var button: ImageButton
    private lateinit var btnSaveBP: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var date: EditText
    private lateinit var details: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentAppointmentReminderBinding.inflate(inflater, container, false)
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
        button = view.findViewById(R.id.addAppointment)
        btnSaveBP = view.findViewById(R.id.btnSaveAppointment)
        date = view.findViewById(R.id.txtDate)
        details = view.findViewById(R.id.txtBP)

        displayData(view)
        Log.e("USER ID", dbHelper.fetchUserId().toString())

        button.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        btnSaveBP.setOnClickListener{
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            //TODO ADD CODE HERE TO SAVE THE INPUT TO THE DATABASE
            dbHelper.insertAppointment(date.text.toString(), details.text.toString(), dbHelper.fetchUserId())
            displayData(view)
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // TODO
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                updateCardViewsAlpha(slideOffset)
            }
        })

    }
    private fun displayData(view: View) {
        val appointData = dbHelper.fetchUserId()?.let { dbHelper.getAppoint(it) } // Fetch the data
        val tableLayout = view.findViewById<TableLayout>(R.id.table)

        // Clear existing rows
        tableLayout.removeAllViews()

        // Add table headers
        val tableHeader = TableRow(requireContext())
        val headerDate = TextView(requireContext())
        val headerBloodPressure = TextView(requireContext())
        val headerDifference = TextView(requireContext())

        headerDate.text = "Date"
        headerBloodPressure.text = "Details"

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
        if (appointData != null) {
            for (record in appointData) {
                val tableRow = TableRow(requireContext())
                val dateTextView = TextView(requireContext())
                val bloodPressureTextView = TextView(requireContext())
                val differenceTextView = TextView(requireContext())

                dateTextView.text = record.date
                bloodPressureTextView.text = record.details.toString()

                // Set background color and padding
                dateTextView.setBackgroundColor(Color.WHITE)
                bloodPressureTextView.setBackgroundColor(Color.WHITE)
                differenceTextView.setBackgroundColor(Color.WHITE)

                dateTextView.setPadding(padding, padding, padding, padding)
                bloodPressureTextView.setPadding(padding, padding, padding, padding)
                differenceTextView.setPadding(padding, padding, padding, padding)

                tableRow.addView(dateTextView)
                tableRow.addView(bloodPressureTextView)
                tableRow.addView(differenceTextView)

                tableLayout.addView(tableRow)
            }
        }
    }
    private fun updateCardViewsAlpha(slideOffset: Float) {
        val alpha = slideOffset
        applyAlphaAnimation(header, alpha)
        applyAlphaAnimation(maincontainer, alpha)
    }

    private fun applyAlphaAnimation(view: View, alpha: Float) {
        val animation = AlphaAnimation(view.alpha, alpha)
        animation.duration = 200 // Set the duration of the fade animation
        animation.interpolator = DecelerateInterpolator()
        animation.fillAfter = true
        view.startAnimation(animation)
        view.alpha = alpha
    }
}
