import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.safenest.DatabaseHelper
import com.example.safenest.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class fragment_home : Fragment() {
    private lateinit var homeNameTextView: TextView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var homeText : TextView
    private lateinit var trimester : TextView
    private lateinit var image : ImageView
    private lateinit var bar: ProgressBar
    private lateinit var week: TextView
    private var username: String? = null
    private var userId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString("username")
        }
        dbHelper = DatabaseHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeNameTextView = view.findViewById(R.id.HomeName)
        trimester = view.findViewById(R.id.HomeTrimester)
        homeText = view.findViewById(R.id.HomeDynamicText)
        image= view.findViewById(R.id.HomeDynamicImage)
        week = view.findViewById(R.id.HomeWeeks)
        bar = view.findViewById(R.id.progressBar)

        val intent = requireActivity().intent
        if (intent != null && intent.hasExtra("user_id")) {
            userId = intent.getLongExtra("user_id", 0)
        }
        Toast.makeText(requireContext(), userId.toString(), Toast.LENGTH_SHORT).show()
        userId?.let { dbHelper.setUserId(it) }

        val name = userId?.let { dbHelper.getUsername(it) }
        homeNameTextView.text = """Hello $name"""

        getDueDate()
        return view
    }
    companion object {
        fun newInstance(username: String?): fragment_home {
            val fragment = fragment_home()
            val args = Bundle().apply {
                putString("username", username)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private fun getDueDate(){
        val lastPeriod = dbHelper.fetchUserId()?.let { dbHelper.getLastPeriod(it) }
        try {
            val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.US)
            sdf.isLenient = false // Set to strict parsing
            val lastMenstrualDate = sdf.parse(lastPeriod)
            val lastMenstrualCalendar = Calendar.getInstance().apply { time = lastMenstrualDate }

            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            if (lastMenstrualCalendar.get(Calendar.YEAR) >= 2024 && lastMenstrualCalendar.get(Calendar.YEAR) <= currentYear) {
                val dueDate = calculateDueDate(lastMenstrualDate!!)
                val currentWeek = calculateCurrentWeek(lastMenstrualDate)
                week.setText("$currentWeek weeks")
                bar.progress = currentWeek.toInt()
                val currentTrimester = calculateTrimester(currentWeek)
                trimester.setText("$currentTrimester trimester")
                updateDynamicText(currentWeek)
                updateDynamicImage(currentWeek)
            } else {
                Toast.makeText(requireContext(), "This tool only works up to 40 weeks of pregnancy. Please enter a date within the last 40 weeks.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Invalid date format. Please use MM-dd-yyyy", Toast.LENGTH_LONG).show()
        }
    }

    private fun calculateDueDate(lastMenstrualDate: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = lastMenstrualDate
        calendar.add(Calendar.DAY_OF_YEAR, 280) // Adding 280 days for a typical pregnancy duration
        return calendar.time
    }

    private fun calculateCurrentWeek(lastMenstrualDate: Date): Long {
        val currentDate = Calendar.getInstance().time
        val diffInMillies = currentDate.time - lastMenstrualDate.time
        return (diffInMillies / (1000 * 60 * 60 * 24 * 7)) + 1 // Converting milliseconds to weeks and adding 1
    }

    private fun calculateTrimester(currentWeek: Long): String {
        return when {
            currentWeek in 1..13 -> "1st"
            currentWeek in 14..27 -> "2nd"
            currentWeek in 28..42 -> "3rd"
            else -> "[]" // Handle any other case as needed
        }
    }
    private fun updateDynamicText(currentWeek: Long) {
        val resources = resources // Get a reference to the application's resources

        val message = when {
            currentWeek in 1..4 -> resources.getString(R.string.weeks_1_4_message)
            currentWeek in 5..8 -> resources.getString(R.string.weeks_5_8_message)
            currentWeek in 9..12 -> resources.getString(R.string.weeks_9_12_message)
            currentWeek in 13..16 -> resources.getString(R.string.weeks_13_16_message)
            currentWeek in 17..20 -> resources.getString(R.string.weeks_17_20_message)
            currentWeek in 21..26 -> resources.getString(R.string.weeks_21_26_message)
            currentWeek in 27..30 -> resources.getString(R.string.weeks_27_30_message)
            currentWeek in 31..34 -> resources.getString(R.string.weeks_31_34_message)
            currentWeek in 35..42 -> resources.getString(R.string.weeks_35_42_message)
            else -> resources.getString(R.string.default_pregnancy_message)
        }

        homeText.text = message
    }
    private fun updateDynamicImage(currentWeek: Long) {
        val weekImageName = when (currentWeek) {
            in 1..42 -> {
                val numberString = when (currentWeek.toInt()) {
                    1 -> "one"
                    2 -> "two"
                    3 -> "three"
                    4 -> "four"
                    5 -> "five"
                    6 -> "six"
                    7 -> "seven"
                    8 -> "eight"
                    9 -> "nine"
                    10 -> "ten"
                    11 -> "eleven"
                    12 -> "twelve"
                    13 -> "thirteen"
                    14 -> "fourteen"
                    15 -> "fifteen"
                    16 -> "sixteen"
                    17 -> "seventeen"
                    18 -> "eighteen"
                    19 -> "nineteen"
                    20 -> "twenty"
                    21 -> "twenty_one"
                    22 -> "twenty_two"
                    23 -> "twenty_three"
                    24 -> "twenty_four"
                    25 -> "twenty_five"
                    26 -> "twenty_six"
                    27 -> "twenty_seven"
                    28 -> "twenty_eight"
                    29 -> "twenty_nine"
                    30 -> "thirty"
                    31 -> "thirty_one"
                    32 -> "thirty_two"
                    33 -> "thirty_three"
                    34 -> "thirty_four"
                    35 -> "thirty_five"
                    36 -> "thirty_six"
                    37 -> "thirty_seven"
                    38 -> "thirty_eight"
                    39 -> "thirty_nine"
                    40 -> "forty"
                    41 -> "forty_one"
                    42 -> "forty_two"
                    else -> throw IllegalArgumentException("Week number out of range")
                }
                "week_$numberString"
            }
            else -> "default_week_image"
        }

        val resourceId = resources.getIdentifier(weekImageName, "drawable", requireContext().packageName)
        image.setImageResource(resourceId)
    }

}
