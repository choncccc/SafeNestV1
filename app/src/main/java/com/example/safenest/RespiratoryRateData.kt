import android.graphics.Color
import android.os.Bundle
import android.view.Gravity

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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.safenest.DatabaseHelper
import com.example.safenest.R
import com.example.safenest.databinding.FragmentRespiratoryRateDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class RespiratoryRateData : Fragment() {
    private lateinit var binding: FragmentRespiratoryRateDataBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var header: TextView
    private lateinit var maincontainer: FrameLayout
    private  lateinit var button: ImageButton
    private lateinit var btnSaveRespiratoryRate: Button
    private lateinit var rDate: EditText
    private lateinit var r_data: EditText
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentRespiratoryRateDataBinding.inflate(inflater, container, false)
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
        button = view.findViewById(R.id.addRowsRespiratoryRate)
        btnSaveRespiratoryRate = view.findViewById(R.id.btnSaveRespiratoryRate)
        rDate = view.findViewById<EditText>(R.id.txtDate)
        r_data = view.findViewById<EditText>(R.id.txtBP)

        displayData(view)

        button.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        btnSaveRespiratoryRate.setOnClickListener{
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            // TODO INSERT CODE TO SAVE INPUTS TO DATABASE
            Toast.makeText(requireContext(), "${r_data.text.toString()} ${rDate.text.toString()}", Toast.LENGTH_SHORT).show()
            dbHelper.insertRespiRate(rDate.text.toString(), r_data.text.toString().toInt(), dbHelper.fetchUserId())
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
        val bodyTempData =
            dbHelper.fetchUserId()?.let { dbHelper.getRespiRate(it) } // Fetch the data
        val tableLayout = view.findViewById<TableLayout>(R.id.table)

        // Clear existing rows
        tableLayout.removeAllViews()

        // Add table headers
        val tableHeader = TableRow(requireContext())
        val headerDate = TextView(requireContext())
        val headerBloodPressure = TextView(requireContext())
        val headerDifference = TextView(requireContext())

        headerDate.text = "Date"
        headerBloodPressure.text = "Respiratory Rate"
        headerDifference.text = "Difference"

        // Set background color and padding
        headerDate.setTextColor(Color.WHITE)
        headerDate.typeface = ResourcesCompat.getFont(requireContext(), R.font.glacial)
        headerBloodPressure.setTextColor(Color.WHITE)
        headerBloodPressure.typeface = ResourcesCompat.getFont(requireContext(), R.font.glacial)
        headerDifference.setTextColor(Color.WHITE)
        headerDifference.typeface = ResourcesCompat.getFont(requireContext(), R.font.glacial)

        // Set background color and padding
        headerDate.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.peach))
        headerBloodPressure.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.peach))
        headerDifference.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.peach))

        val padding = 16
        headerDate.setPadding(padding, padding, padding, padding)
        headerBloodPressure.setPadding(padding, padding, padding, padding)
        headerDifference.setPadding(padding, padding, padding, padding)

        // Center align header text
        headerDate.gravity = Gravity.CENTER
        headerBloodPressure.gravity = Gravity.CENTER
        headerDifference.gravity = Gravity.CENTER

        tableHeader.addView(headerDate)
        tableHeader.addView(headerBloodPressure)
        tableHeader.addView(headerDifference)

        tableLayout.addView(tableHeader)

        // Add rows dynamically
        if (bodyTempData != null) {
            for (record in bodyTempData) {
                val tableRow = TableRow(requireContext())
                val dateTextView = TextView(requireContext())
                val bloodPressureTextView = TextView(requireContext())
                val differenceTextView = TextView(requireContext())

                dateTextView.text = record.date
                bloodPressureTextView.text = record.respi_rate.toString()
                differenceTextView.text = record.difference.toString()

                // Set background color and padding
                dateTextView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.f1))
                bloodPressureTextView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.f1))
                differenceTextView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.f1))

                dateTextView.setTextColor(Color.WHITE)
                dateTextView.typeface = ResourcesCompat.getFont(requireContext(), R.font.glacial)
                bloodPressureTextView.setTextColor(Color.WHITE)
                bloodPressureTextView.typeface = ResourcesCompat.getFont(requireContext(), R.font.glacial)
                differenceTextView.setTextColor(Color.WHITE)
                differenceTextView.typeface= ResourcesCompat.getFont(requireContext(), R.font.glacial)

                dateTextView.setPadding(padding, padding, padding, padding)
                bloodPressureTextView.setPadding(padding, padding, padding, padding)
                differenceTextView.setPadding(padding, padding, padding, padding)

                // Center align row text
                dateTextView.gravity = Gravity.CENTER
                bloodPressureTextView.gravity = Gravity.CENTER
                differenceTextView.gravity = Gravity.CENTER

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