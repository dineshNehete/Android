package com.example.dobcalc

import android.app.DatePickerDialog
import android.icu.util.IslamicCalendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_date_picker = findViewById<Button>(R.id.btn_date_picker)
        tvSelectedDate = findViewById(R.id.selected_date)
        tvAgeInMinutes = findViewById(R.id.time_minutes)
        btn_date_picker.setOnClickListener{
            clickDatePicker()
        }

    }
    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val date_picker_dialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this, "Year was $selectedYear, month was ${selectedMonth + 1}, day was $selectedDayOfMonth", Toast.LENGTH_SHORT).show()
            val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            tvSelectedDate?.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)
            // type safety, run the code inside let only if theDate is not null
            theDate?.let {
                val selectedDateInMinutes = theDate.time / 60000;
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000
                    val difference = currentDateInMinutes - selectedDateInMinutes;
                    tvAgeInMinutes?.text = difference.toString()
                }
            }

        },year, month,day)
        date_picker_dialog.datePicker.maxDate = System.currentTimeMillis() - 86400000
        date_picker_dialog.show()

    }
}