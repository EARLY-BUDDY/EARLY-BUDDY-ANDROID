package com.devaon.early_buddy_android.feature

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_schdule.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduleActivity : AppCompatActivity(){
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schdule)

        val actScheduleName: EditText = findViewById(R.id.act_schedule_et_name)

        setCurrentDate()
        showDatePicker()
        showTimePicker()
        setNotiSpinner()
        setWeekPressed()

    }

    fun setCurrentDate(){
        val current = LocalDateTime.now()
        val dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val dateFormatted = current.format(dateFormat)
        val timeFormat = DateTimeFormatter.ofPattern("a hh:mm")
        val timeFormatted = current.format(timeFormat)

        act_schedule_tv_date_click.setText(dateFormatted)
        act_schedule_tv_time_click.setText(timeFormatted)
    }

    fun showDatePicker(){
        act_schedule_tv_date_click.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener{ datePicker, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                act_schedule_tv_date_click.text = SimpleDateFormat("yyyy.MM.dd").format(cal.time)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    fun showTimePicker(){
        act_schedule_tv_time_click.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                act_schedule_tv_time_click.text = SimpleDateFormat("a hh:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), false).show()
        }
    }

    fun setNotiSpinner(){
        val notiSpinner: Spinner = findViewById(R.id.act_schedule_sp_noti)
        ArrayAdapter.createFromResource(this@ScheduleActivity, R.array.noti_array, android.R.layout.simple_spinner_item)
            .also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                notiSpinner.adapter = adapter
            }
        notiSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    fun setWeekPressed(){

        val mon: ImageView = findViewById(R.id.act_schedule_iv_mon)
        val tue: ImageView = findViewById(R.id.act_schedule_iv_tue)
        val wed: ImageView = findViewById(R.id.act_schedule_iv_wed)
        val thu: ImageView = findViewById(R.id.act_schedule_iv_thu)
        val fri: ImageView = findViewById(R.id.act_schedule_iv_fri)
        val sat: ImageView = findViewById(R.id.act_schedule_iv_sat)
        val sun: ImageView = findViewById(R.id.act_schedule_iv_sun)

        mon.setOnClickListener {
            if(mon.isSelected){
                mon.setSelected(false)
            }else{
                mon.setSelected(true)
            }
        }
        tue.setOnClickListener {
            if(tue.isSelected){
                tue.setSelected(false)
            }else{
                tue.setSelected(true)
            }
        }
        wed.setOnClickListener {
            if(wed.isSelected){
                wed.setSelected(false)
            }else{
                wed.setSelected(true)
            }
        }
        thu.setOnClickListener {
            if(thu.isSelected){
                thu.setSelected(false)
            }else{
                thu.setSelected(true)
            }
        }
        fri.setOnClickListener {
            if(fri.isSelected){
                fri.setSelected(false)
            }else{
                fri.setSelected(true)
            }
        }
        sat.setOnClickListener {
            if(sat.isSelected){
                sat.setSelected(false)
            }else{
                sat.setSelected(true)
            }
        }
        sun.setOnClickListener {
            if(sun.isSelected){
                sun.setSelected(false)
            }else{
                sun.setSelected(true)
            }
        }
    }

}