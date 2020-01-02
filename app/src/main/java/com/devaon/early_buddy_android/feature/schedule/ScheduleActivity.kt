package com.devaon.early_buddy_android.feature.schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import com.devaon.early_buddy_android.R
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.devaon.early_buddy_android.data.schedule.PostScheduleData
import com.devaon.early_buddy_android.data.schedule.UserPath
import com.devaon.early_buddy_android.feature.place.search.text.PlaceDirectionsActivity
import com.devaon.early_buddy_android.feature.place.search.text.PlaceSelectActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_schdule.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*



class ScheduleActivity : AppCompatActivity(){
    var cal = Calendar.getInstance()
    var arriveCount = 0
    var noticeMin = 0

    object schedulePlace {
        var startPlaceName = ""
        var startPlaceX = 0.0
        var startPlaceY = 0.0
        var endPlaceName = ""
        var endPlaceX = 0.0
        var endPlaceY = 0.0
    }


    object scheduleUserPath {
        lateinit var userPath : UserPath
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schdule)


        setCurrentDate()
        showDatePicker()
        showTimePicker()
        setNotiSpinner()
        setNotiRangeSpinner()
        setWeekPressed()
        searchRoute()
        checkValue()

        //장소 textView가 null이 아니라면 defaut 경로 부분을 안보이게 해줘야함
        //null이라면 default 경로가 보이게 해야함
        val route:TextView = findViewById(R.id.act_schedule_tv_route)
        val routeS:TextView = findViewById(R.id.act_schedult_tv_route_selected)
        route.setVisibility(View.GONE)
        routeS.setVisibility(View.GONE)

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
            DatePickerDialog(this@ScheduleActivity, R.style.MyDatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener{ datePicker, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    act_schedule_tv_date_click.text = SimpleDateFormat("yyyy.MM.dd").format(cal.time)

                },cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    fun showTimePicker(){

        act_schedule_tv_time_click.setOnClickListener {
            TimePickerDialog(this@ScheduleActivity, R.style.MyTimePickerDialogTheme,
                TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)
                    act_schedule_tv_time_click.text = SimpleDateFormat("a hh:mm").format(cal.time)
                },cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), false).show()
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
                var userNoti = notiSpinner.selectedItemPosition
                when(userNoti){
                    0 -> arriveCount = 2
                    1 -> arriveCount = 3
                    2 -> arriveCount = 4
                    3 -> arriveCount = 1
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun setNotiRangeSpinner(){
        val notiRangeSpinner: Spinner = findViewById(R.id.act_schedule_sp_noti_range)
        ArrayAdapter.createFromResource(this@ScheduleActivity, R.array.noti_range_array, android.R.layout.simple_spinner_item)
            .also {
                    adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                notiRangeSpinner.adapter = adapter
            }
        notiRangeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var userNotiRange = notiRangeSpinner.selectedItemPosition
                when(userNotiRange){
                    0 -> noticeMin = 5
                    1 -> noticeMin = 10
                    2 -> noticeMin = 20
                    3 -> noticeMin = 0
                }
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

    fun searchRoute(){
        val placeClick = findViewById<ConstraintLayout>(R.id.act_schedule_cl_place_click)

        placeClick.setOnClickListener{
            val intent = Intent(this@ScheduleActivity, PlaceSelectActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        val totalTime = 115.0
        val firstWalkTime = 10.0
        val secondWalkTime = 10.0
        val thirdWalkTime = 5.0
        val method1Time = 50.0
        val method2Time = 20.0
        val method3Time = 18.0



        var totalPath = findViewById<ImageView>(R.id.act_schedule_route_iv_gray_line).width.toDouble()
        val method1 = findViewById<ConstraintLayout>(R.id.act_schedule_route_cl_method_1)
        val method2 = findViewById<ConstraintLayout>(R.id.act_schedule_route_cl_method_2)
        val method3 = findViewById<ConstraintLayout>(R.id.act_schedule_route_cl_method_3)


        val method1Len = totalPath / ((totalTime / method1Time).toFloat()) //totalPath에서 (totalTime / method1Time)만큼의 비율을 차지
        val method2Len = totalPath / ((totalTime / method2Time).toFloat())
        val method3Len = totalPath / ((totalTime / method3Time).toFloat())

        Log.e("length", "total: $totalPath 1: $method1Len 2: $method2Len")
        Log.e("thidthisthis", (method1Time/totalTime).toFloat().toString())
        Log.e("plus2", (totalTime / (firstWalkTime+method1Time+secondWalkTime).toFloat()).toString())

        val method1Margin = totalPath / (totalTime / firstWalkTime)
        val method2Margin = (totalPath / (totalTime / (firstWalkTime+method1Time+secondWalkTime).toFloat())).toInt()
        val method3Margin = (totalPath / (totalTime / (firstWalkTime+method1Time+secondWalkTime+method2Time+thirdWalkTime).toFloat())).toInt()

        Log.e("length", "method1Margin: $method1Margin method2Margin: $method2Margin method3Margin: $method3Margin")

        val method1Params = method1.layoutParams  as ConstraintLayout.LayoutParams
        method1Params.width = method1Len.toInt()
        method1Params.marginStart = method1Margin.toInt()
        method1.layoutParams = method1Params

        val method2Params = method2.layoutParams  as ConstraintLayout.LayoutParams
        method2Params.width = method2Len.toInt()
        method2Params.marginStart = method2Margin
        method2.layoutParams = method2Params

        val method3Params = method3.layoutParams  as ConstraintLayout.LayoutParams
        method3Params.width = method3Len.toInt()
        method3Params.marginStart = method3Margin
        method3.layoutParams = method3Params
    }

    fun checkValue(){

        act_schedule_tv_register.setOnClickListener {
            val scheName = findViewById<EditText>(R.id.act_schedule_et_name).text.toString()

            if (scheName.isEmpty() == true) {
                Toast.makeText(this, "내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                postSchedule(scheName)
                ScheduleDialogFragment {
                    finish()
                }.apply {
                    show(supportFragmentManager, null)
                }
            }
        }
    }

    private fun postSchedule(scheName: String){

        var jsonObject = JSONObject()
        jsonObject.put("scheduleName", scheName)
        jsonObject.put("arriveCount", arriveCount)
        jsonObject.put("noticeMin", noticeMin)
        jsonObject.put("scheduleStartDay", SimpleDateFormat("yyyy-MM-dd").format(cal.time))
        jsonObject.put("scheduleStartTime", SimpleDateFormat("HH:mm").format(cal.time))

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        Log.e("bodybodybodybody", body.toString())
        val callPostSchedule: Call<PostScheduleData> = EarlyBuddyServiceImpl.service.postSchedule(body)

        callPostSchedule.enqueue(object : Callback<PostScheduleData> {
            override fun onFailure(call: Call<PostScheduleData>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(call: Call<PostScheduleData>, response: Response<PostScheduleData>) {
                if (response.isSuccessful) {
                    Log.e("result is ", response.body().toString())
                    val signupUser = response.body()!!
//                    signinDialog.show(supportFragmentManager,"signin_fagment")
                }
            }
        })

    }
}
