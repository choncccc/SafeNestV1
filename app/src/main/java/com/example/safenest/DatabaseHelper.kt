package com.example.safenest
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    data class BloodPressureRecord(val date: String, val bpData: Int, val difference: Int)
    data class BloodSugarRecord(val date: String, val bsData: Int, val difference: Int)
    data class BloodTempRecord(val date: String, val btData: Int, val difference: Int)
    data class PulseRatedRecord(val date: String, val pulse: Int, val difference: Int)
    data class RespiratoryRecord(val date: String, val respi_rate: Int, val difference: Int)
    data class OxygenSatRecord(val date: String, val oxygen: Int, val difference: Int)
    data class WeightRecord(val date: String, val week: String, val weight: Int, val difference: Int)
    data class AppointRecord(val date: String, val details: String)
    data class TodoRecord(val date: String, val details: String)
    data class MedRecord(val name: String, val frequency: String, val sched_mode: String, val sched_time: String, val dosage: Int)
    data class BioRecord(val age: Int, val weight: Int, val height: Int)
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "(SafeNestDB).sqlite"

        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseHelper(context).also { INSTANCE = it }
            }
        }

        //USER PROFILE
        private const val USER_ID = "user_id"
        private const val DISPLAY_NAME = "display_name"
        private const val FIRST_NAME = "first_name"
        private const val LAST_NAME = "last_name"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
        private const val PHONE_NO = "phone_number"
        private const val EMAIL = "email"
        private const val LOGIN_COUNTER = "login_counter"
        private const val LAST_PERIOD = "last_period"
        private const val ESTIMATED_DUE_DATE = "estimated_due_date"

        private const val INPUT_DATE = "input_date"
        private const val CHANGE = "change"

        //PULSE RATE
        private const val PULSE_ID = "pulse_id"
        private const val PULSE_RATE = "pulse_rate"

        //BLOOD PRESSURE
        private const val BP_ID = "bp_id"
        private const val BLOOD_PRESSURE = "blood_pressure"

        //BLOOD SUGAR
        private const val BS_ID = "bs_id"
        private const val BLOOD_SUGAR = "blood_sugar"

        //BODY TEMPERATURE
        private const val BT_ID = "bt_id"
        private const val BODY_TEMPERATURE = "body_temperature"

        //RESPIRATORY RATE
        private const val RR_ID = "rr_id"
        private const val RESPIRATORY_RATE = "respirator_rate"

        //OXYGEN SATURATION
        private const val OS_ID = "os_id"
        private const val OXYGEN_SATURATION = "oxygen_saturation"

        //APPOINTMENT
        private const val APPOINTMENT_ID = "appoint_id"
        private const val APPOINTMENT_DATE = "appoint_date"
        private const val APPOINTMENT_DETAILS = "appoint_details"

        //TODO
        private const val TODO_ID = "todo_id"
        private const val TODO_DATE = "todo_date"
        private const val TODO_DETAILS = "todo_details"

        //MEDICATION
        private const val MED_ID = "med_id"
        private const val MED_NAME = "med_name"
        private const val FREQUENCY = "frequency"
        private const val SCHED_MODE = "sched_mode"
        private const val SCHED_TIME = "sched_time"
        private const val DOSAGE = "dosage"

        //BIOMETRICS
        private const val AGE = "age"
        private const val WEIGHT = "weight"
        private const val HEIGHT = "height"

        //WEIGHT TRACKER
    }

    private val CREATE_TBL_USER = """
        CREATE TABLE user (
            $USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $DISPLAY_NAME TEXT,
            $FIRST_NAME TEXT,
            $LAST_NAME TEXT,
            $USERNAME TEXT,
            $PASSWORD TEXT,
            $PHONE_NO INTEGER,
            $EMAIL TEXT,
            $LOGIN_COUNTER INTEGER DEFAULT 0,
            $LAST_PERIOD DATE,
            $ESTIMATED_DUE_DATE DATE
        )
    """.trimIndent()

    private val CREATE_TBL_PULSE = """
    CREATE TABLE pulse_rate (
        $PULSE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $PULSE_RATE INTEGER NOT NULL,
        $INPUT_DATE DATE NOT NULL,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_BP = """
    CREATE TABLE blood_pressure (
        $BP_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $BLOOD_PRESSURE INTEGER NOT NULL,
        $INPUT_DATE DATE NOT NULL,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_BS = """
    CREATE TABLE blood_sugar (
        $BS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $BLOOD_SUGAR INTEGER NOT NULL,
        $INPUT_DATE DATE NOT NULL,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_BT = """
    CREATE TABLE blood_temp (
        $BT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $BODY_TEMPERATURE INTEGER NOT NULL,
        $INPUT_DATE DATE NOT NULL,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_RR = """
    CREATE TABLE respiratory_rate (
        $RR_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $RESPIRATORY_RATE INTEGER NOT NULL,
        $INPUT_DATE DATE NOT NULL,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_OS = """
    CREATE TABLE oxygen_sat (
        $OS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $OXYGEN_SATURATION INTEGER NOT NULL,
        $INPUT_DATE DATE NOT NULL,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_APPOINT = """
    CREATE TABLE appointment (
        $APPOINTMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $APPOINTMENT_DATE DATE,
        $APPOINTMENT_DETAILS TEXT,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_TODO = """
    CREATE TABLE todo (
        $TODO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $TODO_DATE DATE,
        $TODO_DETAILS TEXT,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_MED = """
    CREATE TABLE medication (
        $MED_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $MED_NAME TEXT,
        $FREQUENCY TEXT,
        $SCHED_MODE TEXT,
        $SCHED_TIME DATE,
        $DOSAGE INTEGER,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_BIO = """
    CREATE TABLE biometrics (
        bio_id INTEGER PRIMARY KEY AUTOINCREMENT,
        $AGE INTEGER,
        $WEIGHT INTEGER,
        $HEIGHT INTEGER,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val CREATE_TBL_WEIGHT = """
    CREATE TABLE weight (
        weight_id INTEGER PRIMARY KEY AUTOINCREMENT,
        $INPUT_DATE DATE,
        $WEIGHT INTEGER,
        $USER_ID INTEGER NOT NULL,
        FOREIGN KEY ($USER_ID) REFERENCES user($USER_ID)
    )
    """.trimIndent()

    private val SET_USERID = """
        CREATE TABLE user_id (
            user_id INTEGER
        )
    """.trimIndent()

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TBL_USER)
        db.execSQL(CREATE_TBL_PULSE)
        db.execSQL(CREATE_TBL_BP)
        db.execSQL(CREATE_TBL_BS)
        db.execSQL(CREATE_TBL_BT)
        db.execSQL(CREATE_TBL_RR)
        db.execSQL(CREATE_TBL_OS)
        db.execSQL(CREATE_TBL_APPOINT)
        db.execSQL(CREATE_TBL_MED)
        db.execSQL(CREATE_TBL_TODO)
        db.execSQL(CREATE_TBL_BIO)
        db.execSQL(CREATE_TBL_WEIGHT)
        db.execSQL(SET_USERID)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Alter tables here
    }

    fun fetchUserId(): Int {
        // Get readable database
        val db = this.readableDatabase

        // Query the database
        val cursor = db.query(
            "user_id",                // The table to query
            arrayOf("user_id"),  // The columns to return
            null,             // The columns for the WHERE clause
            null,         // The values for the WHERE clause
            null,                  // Don't group the rows
            null,                  // Don't filter by row groups
            null                   // The sort order
        )

        // Check if any results were returned
        val loginCounter = if (cursor.moveToFirst()) {
            cursor.getLong(cursor.getColumnIndexOrThrow("user_id"))
        } else {
            null
        }

        // Close the cursor and database
        cursor.close()

        return loginCounter?.toInt()!!
    }

    fun setUserId(userId: Long) {
        val db = this.writableDatabase

        val cursor = db.query(
            "user_id",
            arrayOf("user_id"),
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            // Username exists, update it
            val existingUserID = cursor.getString(with(cursor) { getColumnIndex("user_id") })
            if (existingUserID != userId.toString()) {
                val values = ContentValues().apply {
                    put("user_id", userId)
                }
                db.update("user_id", values, "user_id = ?", arrayOf(existingUserID))
            }
        } else {
            // Username doesn't exist, insert it
            val values = ContentValues().apply {
                put("user_id", userId)
            }
            db.insert("user_id", null, values)
        }

        cursor.close()
        db.close()
    }


    fun getBiometrics(userId: Int): Array<BioRecord> {
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "biometrics",       // The table to query
            arrayOf(AGE, WEIGHT, HEIGHT),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            null            // Order by date ascending
        )

        // Create a list to hold the results
        val bio = mutableListOf<BioRecord>()

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val age = cursor.getInt(cursor.getColumnIndexOrThrow(AGE))
                val weight = cursor.getInt(cursor.getColumnIndexOrThrow(WEIGHT))
                val height = cursor.getInt(cursor.getColumnIndexOrThrow(HEIGHT))

                // Add the current record to the list
               bio.add(BioRecord(age, weight, height))
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()

        // Convert the list to an array and return it
        return bio.toTypedArray()
    }

    fun insertBiometrics(age: Int, weight: Int, height: Int, userId: Int) {
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(AGE, age)
            put(WEIGHT, weight)
            put(HEIGHT, height)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("biometrics", null, values)

        // Close the database
        db.close()
    }

    fun getWeight(userId: Int): Array<WeightRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get weight records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "weight",       // The table to query
            arrayOf(INPUT_DATE, WEIGHT),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            "$INPUT_DATE ASC"       // Order by date ascending
        )

        // Create a list to hold the results
        val weight = mutableListOf<WeightRecord>()
        val firstWeight = getBiometrics(userId).first().weight
        val lastPeriod = getLastPeriod(userId)
        if (lastPeriod != null) {
            Log.e("LAST PERIOD", lastPeriod)
        }else {
            Log.e("NULLL", "NULLL")
        }

        // Date formatter
        val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val lastPeriodDate = dateFormat.parse(lastPeriod)

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(INPUT_DATE))
                val oData = cursor.getInt(cursor.getColumnIndexOrThrow(WEIGHT))

                val currentDate = dateFormat.parse(date)
                // Calculate the difference in days
                val diffInMillis = currentDate.time - lastPeriodDate.time
                val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
                // Calculate the week of pregnancy
                val week = (diffInDays / 7).toInt() + 1
                val weekString = "week $week"

                val difference = oData - firstWeight
                // Add the current record to the list
                weight.add(WeightRecord(date, weekString, oData, difference))
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return weight.toTypedArray()
    }


    fun insertWeight(bpDate: String, wData: Int, userId: Int) {
        // Define the input and output date formats
        val inputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

        // Parse the input date string to a Date object
        val parsedDate: Date? = inputDateFormat.parse(bpDate)

        // Format the Date object to the desired output format
        val formattedDate = parsedDate?.let { outputDateFormat.format(it) } ?: bpDate

        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(INPUT_DATE, formattedDate)
            put(WEIGHT, wData)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("weight", null, values)

        // Close the database
        db.close()
    }

    fun getMedication(userId: Int): Array<MedRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "medication",       // The table to query
            arrayOf(MED_NAME, FREQUENCY, SCHED_TIME, SCHED_MODE, DOSAGE),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            null              // Order by date ascending
        )

        // Create a list to hold the results
        val meds = mutableListOf<MedRecord>()

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(MED_NAME))
                val frequency = cursor.getString(cursor.getColumnIndexOrThrow(FREQUENCY))
                val sched_time = cursor.getString(cursor.getColumnIndexOrThrow(SCHED_TIME))
                val sched_mode = cursor.getString(cursor.getColumnIndexOrThrow(SCHED_MODE))
                val dosage = cursor.getInt(cursor.getColumnIndexOrThrow(DOSAGE))

                meds.add(MedRecord(name, frequency, sched_mode, sched_time, dosage))
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return meds.toTypedArray()
    }

    fun insertMedication(medName: String, sched_mode: String, sched_time: String, frequency: String, dosage: Int, userId: Int) {
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(MED_NAME, medName)
            put(SCHED_MODE, sched_mode)
            put(SCHED_TIME, sched_time)
            put(FREQUENCY, frequency)
            put(DOSAGE, dosage)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("medication", null, values)

        // Close the database
        db.close()
    }

    fun getTodo(userId: Int): Array<TodoRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "appointment",       // The table to query
            arrayOf(TODO_DATE, TODO_DETAILS),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            "$TODO_DATE ASC"              // Order by date ascending
        )

        // Create a list to hold the results
        val oRecords = mutableListOf<TodoRecord>()

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(TODO_DATE))
                val oData = cursor.getString(cursor.getColumnIndexOrThrow(TODO_DETAILS))

                // Add the current record to the list
                oRecords.add(TodoRecord(date, oData))
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return oRecords.toTypedArray()
    }

    fun insertTodo(bpDate: String, rData: String, userId: Int) {
        // Define the input and output date formats
        val inputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

        // Parse the input date string to a Date object
        val parsedDate: Date? = inputDateFormat.parse(bpDate)

        // Format the Date object to the desired output format
        val formattedDate = parsedDate?.let { outputDateFormat.format(it) } ?: bpDate

        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(TODO_DATE, formattedDate)
            put(TODO_DETAILS, rData)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("appointment", null, values)

        // Close the database
        db.close()
    }

    fun getAppoint(userId: Int): Array<AppointRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "appointment",       // The table to query
            arrayOf(APPOINTMENT_DATE, APPOINTMENT_DETAILS),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            "$APPOINTMENT_DATE ASC"              // Order by date ascending
        )

        // Create a list to hold the results
        val oRecords = mutableListOf<AppointRecord>()

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(APPOINTMENT_DATE))
                val oData = cursor.getString(cursor.getColumnIndexOrThrow(APPOINTMENT_DETAILS))

                // Add the current record to the list
                oRecords.add(AppointRecord(date, oData))
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return oRecords.toTypedArray()
    }

    fun insertAppointment(bpDate: String, rData: String, userId: Int) {
        // Define the input and output date formats
        val inputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

        // Parse the input date string to a Date object
        val parsedDate: Date? = inputDateFormat.parse(bpDate)

        // Format the Date object to the desired output format
        val formattedDate = parsedDate?.let { outputDateFormat.format(it) } ?: bpDate

        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(APPOINTMENT_DATE, formattedDate)
            put(APPOINTMENT_DETAILS, rData)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("appointment", null, values)

        // Close the database
        db.close()
    }

    fun getOxygenSat(userId: Int): Array<OxygenSatRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "oxygen_sat",       // The table to query
            arrayOf(INPUT_DATE, OXYGEN_SATURATION),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            "$INPUT_DATE ASC"              // Order by date ascending
        )

        // Create a list to hold the results
        val oRecords = mutableListOf<OxygenSatRecord>()

        // Variables to hold the previous blood pressure data
        var previousOData: Int? = null

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(INPUT_DATE))
                val oData = cursor.getInt(cursor.getColumnIndexOrThrow(OXYGEN_SATURATION))

                // Calculate the difference from the previous record
                val difference = if (previousOData != null) {
                    previousOData- oData!!
                } else {
                    0
                }

                // Add the current record to the list
                oRecords.add(OxygenSatRecord(date, oData, difference))

                // Update previousBpData to the current bpData
                previousOData = oData
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return oRecords.toTypedArray()
    }

    fun insertOxygenSat(bpDate: String, rData: Int, userId: Int) {
        // Define the input and output date formats
        val inputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

        // Parse the input date string to a Date object
        val parsedDate: Date? = inputDateFormat.parse(bpDate)

        // Format the Date object to the desired output format
        val formattedDate = parsedDate?.let { outputDateFormat.format(it) } ?: bpDate

        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(INPUT_DATE, formattedDate)
            put(OXYGEN_SATURATION, rData)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("oxygen_sat", null, values)

        // Close the database
        db.close()
    }

    fun getRespiRate(userId: Int): Array<RespiratoryRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "respiratory_rate",       // The table to query
            arrayOf(INPUT_DATE, RESPIRATORY_RATE),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            "$INPUT_DATE ASC"              // Order by date ascending
        )

        // Create a list to hold the results
        val rRecords = mutableListOf<RespiratoryRecord>()

        // Variables to hold the previous blood pressure data
        var previousRData: Int? = null

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(INPUT_DATE))
                val rData = cursor.getInt(cursor.getColumnIndexOrThrow(RESPIRATORY_RATE))

                // Calculate the difference from the previous record
                val difference = if (previousRData != null) {
                    previousRData- rData!!
                } else {
                    0
                }

                // Add the current record to the list
                rRecords.add(RespiratoryRecord(date, rData, difference))

                // Update previousBpData to the current bpData
                previousRData = rData
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return rRecords.toTypedArray()
    }

    fun insertRespiRate(bpDate: String, rData: Int, userId: Int) {
        // Define the input and output date formats
        val inputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

        // Parse the input date string to a Date object
        val parsedDate: Date? = inputDateFormat.parse(bpDate)

        // Format the Date object to the desired output format
        val formattedDate = parsedDate?.let { outputDateFormat.format(it) } ?: bpDate

        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(INPUT_DATE, formattedDate)
            put(RESPIRATORY_RATE, rData)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("respiratory_rate", null, values)

        // Close the database
        db.close()
    }

    fun getPulseRate(userId: Int): Array<PulseRatedRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "pulse_rate",       // The table to query
            arrayOf(INPUT_DATE, PULSE_RATE),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            "$INPUT_DATE ASC"              // Order by date ascending
        )

        // Create a list to hold the results
        val pRecords = mutableListOf<PulseRatedRecord>()

        // Variables to hold the previous blood pressure data
        var previousPData: Int? = null

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(INPUT_DATE))
                val pulseData = cursor.getInt(cursor.getColumnIndexOrThrow(PULSE_RATE))

                // Calculate the difference from the previous record
                val difference = if (previousPData != null) {
                     previousPData- pulseData!!
                } else {
                    0
                }

                // Add the current record to the list
                pRecords.add(PulseRatedRecord(date, pulseData, difference))

                // Update previousBpData to the current bpData
                previousPData = pulseData
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return pRecords.toTypedArray()
    }

    fun insertPulseRate(bpDate: String, pulseData: Int, userId: Int) {
        // Define the input and output date formats
        val inputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

        // Parse the input date string to a Date object
        val parsedDate: Date? = inputDateFormat.parse(bpDate)

        // Format the Date object to the desired output format
        val formattedDate = parsedDate?.let { outputDateFormat.format(it) } ?: bpDate

        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(INPUT_DATE, formattedDate)
            put(PULSE_RATE, pulseData)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("pulse_rate", null, values)

        // Close the database
        db.close()
    }

    fun getBodyTemp(userId: Int): Array<BloodTempRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "blood_temp",       // The table to query
            arrayOf(INPUT_DATE, BODY_TEMPERATURE),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            "$INPUT_DATE ASC"              // Order by date ascending
        )

        // Create a list to hold the results
        val btRecords = mutableListOf<BloodTempRecord>()

        // Variables to hold the previous blood pressure data
        var previousBtData: Int? = null

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(INPUT_DATE))
                val btData = cursor.getInt(cursor.getColumnIndexOrThrow(BODY_TEMPERATURE))

                // Calculate the difference from the previous record
                val difference = if (previousBtData != null) {
                     previousBtData- btData!!
                } else {
                    0
                }

                // Add the current record to the list
                btRecords.add(BloodTempRecord(date, btData, difference))

                // Update previousBpData to the current bpData
                previousBtData = btData
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return btRecords.toTypedArray()
    }

    fun insertBodyTemp(bpDate: String, btData: Int, userId: Int) {
        // Define the input and output date formats
        val inputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

        // Parse the input date string to a Date object
        val parsedDate: Date? = inputDateFormat.parse(bpDate)

        // Format the Date object to the desired output format
        val formattedDate = parsedDate?.let { outputDateFormat.format(it) } ?: bpDate

        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(INPUT_DATE, formattedDate)
            put(BODY_TEMPERATURE, btData)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("blood_temp", null, values)

        // Close the database
        db.close()
    }

    fun getBloodSugar(userId: Int): Array<BloodSugarRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "blood_sugar",       // The table to query
            arrayOf(INPUT_DATE, BLOOD_SUGAR),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            "$INPUT_DATE ASC"              // Order by date ascending
        )

        // Create a list to hold the results
        val bsRecords = mutableListOf<BloodSugarRecord>()

        // Variables to hold the previous blood pressure data
        var previousBsData: Int? = null

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(INPUT_DATE))
                val bsData = cursor.getInt(cursor.getColumnIndexOrThrow(BLOOD_SUGAR))

                // Calculate the difference from the previous record
                val difference = if (previousBsData != null) {
                    previousBsData- bsData!!
                } else {
                    0
                }

                // Add the current record to the list
                bsRecords.add(BloodSugarRecord(date, bsData, difference))

                // Update previousBpData to the current bpData
                previousBsData = bsData
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return bsRecords.toTypedArray()
    }

    fun insertBloodSugar(bsDate: String, bsData: Int, userId: Int) {
        // Define the input and output date formats
        val inputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

        // Parse the input date string to a Date object
        val parsedDate: Date? = inputDateFormat.parse(bsDate)

        // Format the Date object to the desired output format
        val formattedDate = parsedDate?.let { outputDateFormat.format(it) } ?: bsDate

        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(INPUT_DATE, formattedDate)
            put(BLOOD_SUGAR, bsData)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("blood_sugar", null, values)

        // Close the database
        db.close()
    }

    fun getBloodPressure(userId: Int): Array<BloodPressureRecord> {
        // Get readable database
        val db = this.readableDatabase

        // Define the query to get blood pressure records for the given user, ordered by date
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Execute the query
        val cursor = db.query(
            "blood_pressure",       // The table to query
            arrayOf(INPUT_DATE, BLOOD_PRESSURE),  // The columns to return
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // Don't group the rows
            null,                   // Don't filter by row groups
            "$INPUT_DATE ASC"              // Order by date ascending
        )

        // Create a list to hold the results
        val bpRecords = mutableListOf<BloodPressureRecord>()

        // Variables to hold the previous blood pressure data
        var previousBpData: Int? = null

        // Iterate over the cursor to extract the records
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(cursor.getColumnIndexOrThrow(INPUT_DATE))
                val bpData = cursor.getInt(cursor.getColumnIndexOrThrow(BLOOD_PRESSURE))

                // Calculate the difference from the previous record
                val difference = if (previousBpData != null) {
                    previousBpData - bpData!!
                } else {
                    0
                }

                // Add the current record to the list
                bpRecords.add(BloodPressureRecord(date, bpData, difference))

                // Update previousBpData to the current bpData
                previousBpData = bpData
            } while (cursor.moveToNext())
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Convert the list to an array and return it
        return bpRecords.toTypedArray()
    }

    fun insertBloodPressure(bpDate: String, bpData: Int, userId: Int) {
        // Define the input and output date formats
        val inputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val outputDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

        Log.e("DATEEEE", bpDate)
        // Parse the input date string to a Date object
        val parsedDate: Date? = inputDateFormat.parse(bpDate)

        // Format the Date object to the desired output format
        val formattedDate = parsedDate?.let { outputDateFormat.format(it) } ?: bpDate

        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(INPUT_DATE, formattedDate)
            put(BLOOD_PRESSURE, bpData)
            put(USER_ID, userId)
        }

        // Insert the values into the blood_pressure table
        db.insert("blood_pressure", null, values)

        // Close the database
        db.close()
    }



    fun getUsername(userId: Long): String? {
        // Get readable database
        val db = this.readableDatabase

        // Define the query
        val projection = arrayOf(USERNAME)
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Perform the query
        val cursor = db.query(
            "user",            // The table to query
            projection,        // The columns to return
            selection,         // The columns for the WHERE clause
            selectionArgs,     // The values for the WHERE clause
            null,              // Don't group the rows
            null,              // Don't filter by row groups
            null               // The sort order
        )

        // Extract the username from the cursor
        var username: String? = null
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME))
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        return username
    }

    fun getLastPeriod(userId: Int): String? {
        val db = this.readableDatabase

        // Define the query
        val projection = arrayOf(LAST_PERIOD)
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(userId.toString())

        // Log the userId to ensure it's not null
        Log.d("DatabaseHelper", "userId: $userId")

        // Perform the query
        val cursor = db.query(
            "user",            // The table to query
            projection,        // The columns to return
            selection,         // The columns for the WHERE clause
            selectionArgs,     // The values for the WHERE clause
            null,              // Don't group the rows
            null,              // Don't filter by row groups
            null               // The sort order
        )

        // Extract the last period from the cursor
        var lastPeriod: String? = null
        if (cursor.moveToFirst()) {
            lastPeriod = cursor.getString(cursor.getColumnIndexOrThrow(LAST_PERIOD))
            Log.d("DatabaseHelper", "lastPeriod: $lastPeriod")
        } else {
            Log.d("DatabaseHelper", "Cursor is empty")
        }

        // Close the cursor and database
        cursor.close()

        return lastPeriod
    }


    fun insertPregnancyDetails(user_id: Long, last_day: String) {
        // Get writable database
        val db = this.writableDatabase

        // Convert date strings to Date objects
        val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val lastDayDate: Date? = dateFormat.parse(last_day)
        //val estimatedDueDate: Date? = dateFormat.parse(estimatedDue)

        // Format dates to store in the database
        val lastDayFormatted = lastDayDate?.let { dateFormat.format(it) }
        //val estimatedDueFormatted = estimatedDueDate?.let { dateFormat.format(it) }

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(LAST_PERIOD, lastDayFormatted)
            //put(ESTIMATED_DUE_DATE, estimatedDueFormatted)
        }

        // Perform the update operation
        db.update("user", values, "user_id = ?", arrayOf(user_id.toString()))

        // Close the database
        db.close()
    }

    fun insertDisplayName(user_id: Long, displayName: String) {
        // Get writable database
        val db = this.writableDatabase

        // Create a ContentValues object to hold the values to insert
        val values = ContentValues().apply {
            put(DISPLAY_NAME, displayName)
        }

        // Perform the update operation
        db.update("user", values, "user_id = ?", arrayOf(user_id.toString()))

        // Close the database
        db.close()
    }


    fun incrementLoginCounter(user_id: Long) {
        // Get writable database
        val db = this.writableDatabase

        // Define the SQL statement to increment the login counter
        val sql = "UPDATE user SET login_counter = login_counter + 1 WHERE user_id = ?"

        // Execute the SQL statement
        val stmt = db.compileStatement(sql)
        stmt.bindLong(1, user_id)

        // Execute the update
        stmt.executeUpdateDelete()

        // Close the statement and database
        stmt.close()
        db.close()
    }


    fun getLoginCounter(user_id: Long): Long? {
        // Get readable database
        val db = this.readableDatabase

        // Define the query
        val selection = "user_id = ?"
        val selectionArgs = arrayOf(user_id.toString())

        // Query the database
        val cursor = db.query(
            "user",                // The table to query
            arrayOf(LOGIN_COUNTER),  // The columns to return
            selection,             // The columns for the WHERE clause
            selectionArgs,         // The values for the WHERE clause
            null,                  // Don't group the rows
            null,                  // Don't filter by row groups
            null                   // The sort order
        )

        // Check if any results were returned
        val loginCounter = if (cursor.moveToFirst()) {
            cursor.getLong(cursor.getColumnIndexOrThrow(LOGIN_COUNTER))
        } else {
            null
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        return loginCounter
    }


    fun checkUser(username: String, password: String): Long? {
        // Get readable database
        val db = this.readableDatabase

        // Define the query
        val selection = "username = ? AND password = ?"
        val selectionArgs = arrayOf(username, password)

        // Query the database
        val cursor = db.query(
            "user",            // The table to query
            arrayOf(USER_ID),     // The columns to return
            selection,         // The columns for the WHERE clause
            selectionArgs,     // The values for the WHERE clause
            null,              // Don't group the rows
            null,              // Don't filter by row groups
            null               // The sort order
        )

        // Check if any results were returned
        val userId = if (cursor.moveToFirst()) {
            cursor.getLong(cursor.getColumnIndexOrThrow(USER_ID))
        } else {
            null
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        return userId
    }



    fun insertSignUpUser(first_name: String, last_name: String, username: String, password: String, phone_num: String, email: String): Long {
        // Get writable database
        val db = this.writableDatabase

        // Create ContentValues object to store key-value pairs
        val values = ContentValues().apply {
            put(FIRST_NAME, first_name)
            put(LAST_NAME, last_name)
            put(USERNAME, username)
            put(PASSWORD, password)
            put(PHONE_NO, phone_num)
            put(EMAIL, email)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert("user", null, values)

        // Handle insertion result (optional)
        if (newRowId == -1L) {
            // Handle the error if the insertion failed
            Log.e("Database", "Error inserting new user")
        } else {
            // Optionally log the new row ID or take other actions
            Log.d("Database", "User inserted with ID: $newRowId")
        }
        // Close the database (optional)
        db.close()
        return newRowId
    }



}
