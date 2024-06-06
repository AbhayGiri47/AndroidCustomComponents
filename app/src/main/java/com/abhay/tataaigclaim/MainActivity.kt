package com.abhay.tataaigclaim

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.abhay.tataaigclaim.databinding.ActivityMainBinding
import com.abhay.tataaigclaim.otp_edittext.ConsumerOtpEditText
import com.abhay.tataaigclaim.spinner_edittext.SpinnerAdapter
import com.abhay.tataaigclaim.spinner_edittext.SpinnerDataModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val calendar = Calendar.getInstance()
    private var switchChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        setupDropDown()
        setupDateTime()
        setupSwitch()
        setupBNV()
        setupOtpEditText()
    }

    private fun setupOtpEditText() {
        binding.etOtp.setPinViewEventListener(object : ConsumerOtpEditText.PinViewEventListener {
            override fun onDataEntered(pinview: ConsumerOtpEditText?, fromUser: Boolean) {
                Toast.makeText(this@MainActivity, pinview!!.value, Toast.LENGTH_SHORT).show()
            }
        })

        binding.etOtp.apply {
            setTextSize(14)
            setTextColor(Color.BLACK)
            showCursor(true)
        }

        binding.etOtp4.apply {
            setTextSize(14)
            setTextColor(Color.BLACK)
            showCursor(true)
        }
    }

    private fun setupBNV() {

        binding.straightBottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.unassigned->{}
                R.id.assigned->{}
                R.id.completed->{}
            }
            true
        }
    }

    private fun setupSwitch() {

        /*val listener = object : ToggleListener {
            override fun onToggle(view: CustomLabeledSwitch, flag: Boolean) {
                Toast.makeText(this@MainActivity, flag.toString(), Toast.LENGTH_SHORT).show()

            }

        }
        binding.btnSwitch.setOnToggledListener(listener)*/
    }

    private fun setupDateTime() {

        binding.etDate.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            isFocusable = false
            setOnClickListener { showDatePicker() }
        }

        binding.etTime.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            isFocusable = false
            setOnClickListener{showTimePicker()}
        }
    }

    private fun showTimePicker() {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                binding.etTime.setText( SimpleDateFormat("hh:mm a",Locale.getDefault()).format(calendar.time))
            }
            TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                // Format the selected date into a string
                val formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                binding.etDate.setText( formattedDate.toString())
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

    private fun setupDropDown() {

        val items = listOf(
            SpinnerDataModel(R.drawable.calendar, "Workshop Employee"),
            SpinnerDataModel(R.drawable.calendar, "Internal Surveyor"),
            SpinnerDataModel(R.drawable.calendar, "External Surveyor")
        )
//        binding.spinner.getSpinner().adapter = adapter
        binding.spinner.setSpinnerData(items)

    }
}