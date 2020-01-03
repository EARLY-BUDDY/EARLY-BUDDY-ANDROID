package com.devaon.early_buddy_android.feature.schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import com.devaon.early_buddy_android.R
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.devaon.early_buddy_android.data.route.Path
import com.devaon.early_buddy_android.data.schedule.PostScheduleData
import com.devaon.early_buddy_android.feature.place.search.route.PlaceSearchRouteActivity
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.endPlaceName
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.endPlaceX
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.endPlaceY
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.startPlaceName
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.startPlaceX
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.startPlaceY
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.selectedPath.path
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_schdule.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduleActivity : AppCompatActivity(){
    var cal = Calendar.getInstance()
    var arriveCount = 0
    var noticeMin = 0
    var weekdays = arrayListOf<Int>()


    lateinit var route: TextView
    lateinit var routeSelect: TextView
  
    lateinit var mon: ImageView
    lateinit var tue: ImageView
    lateinit var wed: ImageView
    lateinit var thu: ImageView
    lateinit var fri: ImageView
    lateinit var sat: ImageView
    lateinit var sun: ImageView

    lateinit var time: TextView
    lateinit var method: TextView

    lateinit var walk1 : RelativeLayout
    lateinit var walk2 : RelativeLayout
    lateinit var walk3 : RelativeLayout
    lateinit var walk4 : RelativeLayout

    lateinit var method1: RelativeLayout
    lateinit var method2: RelativeLayout
    lateinit var method3: RelativeLayout

    lateinit var method1Img: ImageView
    lateinit var method2Img: ImageView
    lateinit var method3Img: ImageView

    lateinit var method1Tx: TextView
    lateinit var method2Tx: TextView
    lateinit var method3Tx: TextView


    object schedulePlace {
        var startPlaceName = ""
        var startPlaceX = 0.0
        var startPlaceY = 0.0

        var endPlaceName = ""
        var endPlaceX = 0.0
        var endPlaceY = 0.0
    }

    object selectedPath {
         var path: Path? = null
    }

    var scheduleIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schdule)

        route = findViewById(R.id.act_schedule_tv_route)
        routeSelect = findViewById(R.id.act_schedult_tv_route_selected)

        mon = findViewById(R.id.act_schedule_iv_mon)
        tue = findViewById(R.id.act_schedule_iv_tue)
        wed = findViewById(R.id.act_schedule_iv_wed)
        thu = findViewById(R.id.act_schedule_iv_thu)
        fri = findViewById(R.id.act_schedule_iv_fri)
        sat = findViewById(R.id.act_schedule_iv_sat)
        sun = findViewById(R.id.act_schedule_iv_sun)

        time = findViewById(R.id.act_schedule_route_time)
        method = findViewById(R.id.act_schedule_route_tv_method)

        walk1 = findViewById(R.id.act_schedule_route_rl_walk_1)
        walk2 = findViewById(R.id.act_schedule_route_rl_walk_2)
        walk3 = findViewById(R.id.act_schedule_route_rl_walk_3)
        walk4 = findViewById(R.id.act_schedule_route_rl_walk_4)

        method1 = findViewById(R.id.act_schedule__route_rl_method_1)
        method2 = findViewById(R.id.act_schedule_route_rl_method_2)
        method3 = findViewById(R.id.act_schedule_route_rl_method_3)

        method1Img = findViewById(R.id.act_schedule_route_iv_method_1)
        method2Img = findViewById(R.id.act_schedule_route_iv_method_2)
        method3Img = findViewById(R.id.act_schedule_route_iv_method_3)

        method1Tx = findViewById(R.id.act_schedule_route_tv_method_1)
        method2Tx = findViewById(R.id.act_schedule_route_tv_method_2)
        method3Tx = findViewById(R.id.act_schedule_route_tv_method_3)

        setCurrentDate()
        showDatePicker()
        showTimePicker()
        setNotiSpinner()
        setNotiRangeSpinner()
        setWeekPressed()
        searchRoute()
        setPostButton()

    }

    override fun onResume() {
        super.onResume()

        if(path !=  null){
            act_schedule_tv_place_from_result.text = startPlaceName
            act_schedule_tv_place_from_result.setTextColor(Color.parseColor("#3e3e3e"))
            act_schedule_tv_place_to_result.text = endPlaceName
            act_schedule_tv_place_to_result.setTextColor(Color.parseColor("#3e3e3e"))
            route.visibility = View.GONE
            routeSelect.visibility = View.GONE

            setRouteView()
        }
    }

    fun setCurrentDate(){
        val current = LocalDateTime.now()
        val dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val dateFormatted = current.format(dateFormat)
        val timeFormat = DateTimeFormatter.ofPattern("a hh:mm")
        val timeFormatted = current.format(timeFormat)

        act_schedule_tv_date_click.text = dateFormatted
        act_schedule_tv_time_click.text = timeFormatted
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
                    0 -> arriveCount = 1
                    1 -> arriveCount = 2
                    2 -> arriveCount = 3
                    3 -> arriveCount = 0
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

    private fun setWeekPressed(){

        mon.setOnClickListener {
            if(mon.isSelected){
                mon.isSelected = false
            }else{
                mon.isSelected = true
            }
        }
        tue.setOnClickListener {
            if(tue.isSelected){
                tue.isSelected = false
            }else{
                tue.isSelected = true
            }
        }
        wed.setOnClickListener {
            if(wed.isSelected){
                wed.isSelected = false
            }else{
                wed.isSelected = true
            }
        }
        thu.setOnClickListener {
            if(thu.isSelected){
                thu.isSelected = false
            }else{
                thu.isSelected = true
            }
        }
        fri.setOnClickListener {
            if(fri.isSelected){
                fri.isSelected = false
            }else{
                fri.isSelected = true
            }
        }
        sat.setOnClickListener {
            if(sat.isSelected){
                sat.isSelected = false
            }else{
                sat.isSelected = true
            }
        }
        sun.setOnClickListener {
            if(sun.isSelected){
                sun.isSelected = false
            }else{
                sun.isSelected = true
            }
        }
    }

    private fun searchRoute(){
        val placeClick = findViewById<ConstraintLayout>(R.id.act_schedule_cl_place_click)

        placeClick.setOnClickListener{
            val intent = Intent(this@ScheduleActivity, PlaceSearchRouteActivity::class.java)
            intent.putExtra("scheduleDate",SimpleDateFormat("MM월 dd일").format(cal.time))
            intent.putExtra("scheduleDayOfWeek", cal.get(Calendar.DAY_OF_WEEK))
            intent.putExtra("scheduleTime", SimpleDateFormat("a hh:mm").format(cal.time))
            startActivity(intent)

        }
    }

    private fun setRouteView() {
        var transText = arrayListOf<String>()
        var transColor = arrayListOf<String>()

        for (i in 1..selectedPath.path!!.subPath.size - 1) {
            if (i % 2 != 0) { // 홀수만 처리
                when (selectedPath.path!!.subPath[i].trafficType) {
                    1 -> { // 지하철
                        when (selectedPath.path!!.subPath[i].lane.subwayCode) {
                            1 -> {
                                transText.add("1호선")
                                transColor.add("#243899")
                            }
                            2 -> {
                                transText.add("2호선")
                                transColor.add("#35b645")
                            }
                            3 -> {
                                transText.add("3호선")
                                transColor.add("#f36e00")
                            }
                            4 -> {
                                transText.add("4호선")
                                transColor.add("#219de2")
                            }
                            5 -> {
                                transText.add("5호선")
                                transColor.add("#8828e2")
                            }
                            6 -> {
                                transText.add("6호선")
                                transColor.add("#b75000")
                            }
                            7 -> {
                                transText.add("7호선")
                                transColor.add("#697305")
                            }
                            8 -> {
                                transText.add("8호선")
                                transColor.add("#e8146d")
                            }
                            9 -> {
                                transText.add("9호선")
                                transColor.add("#d2a715")
                            }

                            100 -> {
                                transText.add("분당선")
                                transColor.add("#eeaa00")
                            }
                            101 -> {
                                transText.add("공항철도")
                                transColor.add("#70b5e6")
                            }
                            104 -> {
                                transText.add("경의중앙선")
                                transColor.add("#7ac6a4")
                            }
                            107 -> {
                                transText.add("에버라인")
                                transColor.add("#75c56e")
                            }
                            108 -> {
                                transText.add("경춘선")
                                transColor.add("#00b07a")
                            }
                            102 -> {
                                transText.add("자기부상철도")
                                transColor.add("#f08d41")
                            }
                            109 -> {
                                transText.add("신분당선")
                                transColor.add("#a71b2c")
                            }
                            110 -> {
                                transText.add("의정부경전철")
                                transColor.add("#ff9f00")
                            }
                            111 -> {
                                transText.add("수인선")
                                transColor.add("#eeaa00")
                            }
                            112 -> {
                                transText.add("경강선")
                                transColor.add("#1e6ff7")
                            }
                            113 -> {
                                transText.add("우이신설선")
                                transColor.add("#c7c300")
                            }
                            114 -> {
                                transText.add("서해선")
                                transColor.add("#8ac832")
                            }
                        }
                    }
                    2 -> { // 일반
                        transText.add(selectedPath.path!!.subPath[i].lane.type.toString())
                        when (selectedPath.path!!.subPath[i].lane.type) {
                            1, 2, 11 -> transColor.add("#3469ec")
                            10, 12 -> transColor.add("#33c63c")
                            4, 14, 15 -> transColor.add("#ff574c")
                            5 -> transColor.add("#70b5e5")
                            else -> transColor.add("#85c900")
                        }
                    }
                }
            }

        }

        val walkParam1 = walk1.layoutParams as LinearLayout.LayoutParams
        walkParam1.weight = selectedPath.path!!.subPath[0].sectionTime.toFloat()
        walk1.layoutParams = walkParam1

        val methodParam1 = method1.layoutParams as LinearLayout.LayoutParams
        methodParam1.weight = selectedPath.path!!.subPath[1].sectionTime.toFloat()
        method1.layoutParams = methodParam1
        method1Tx.text = transText[0]
        method1Tx.setTextColor(Color.parseColor(transColor[0]))
        method1Img.backgroundTintList = ColorStateList.valueOf(Color.parseColor(transColor[0]))

        if (selectedPath.path!!.subPath.size > 2) {
            val walkParam2 = walk2.layoutParams as LinearLayout.LayoutParams
            walkParam2.weight = selectedPath.path!!.subPath[2].sectionTime.toFloat()
            walk2.layoutParams = walkParam2
        } else {
            walk2.visibility = View.GONE
            method2.visibility = View.GONE
            walk3.visibility = View.GONE
            method3.visibility = View.GONE
            walk4.visibility = View.GONE
        }

        if (selectedPath.path!!.subPath.size > 3) {
            val methodParam2 = method2.layoutParams as LinearLayout.LayoutParams
            methodParam2.weight = selectedPath.path!!.subPath[3].sectionTime.toFloat()
            method2.layoutParams = methodParam2
            method2Tx.text = transText[1]
            method2Tx.setTextColor(Color.parseColor(transColor[1]))
            method2Img.backgroundTintList = ColorStateList.valueOf(Color.parseColor(transColor[1]))
        } else {
            method2.visibility = View.GONE
            walk3.visibility = View.GONE
            method3.visibility = View.GONE
            walk4.visibility = View.GONE
        }

        if (selectedPath.path!!.subPath.size > 4) {
            val walkParam3 = walk3.layoutParams as LinearLayout.LayoutParams
            walkParam3.weight = selectedPath.path!!.subPath[4].sectionTime.toFloat()
            walk3.layoutParams = walkParam3
        } else {
            walk3.visibility = View.GONE
            method3.visibility = View.GONE
            walk4.visibility = View.GONE
        }

        if (selectedPath.path!!.subPath.size > 5) {
            val methodParam3 = method3.layoutParams as LinearLayout.LayoutParams
            methodParam3.weight = selectedPath.path!!.subPath[5].sectionTime.toFloat()
            method3.layoutParams = methodParam3
            method3Tx.text = transText[2]
            method3Tx.setTextColor(Color.parseColor(transColor[2]))
            method3Img.backgroundTintList = ColorStateList.valueOf(Color.parseColor(transColor[2]))
        } else {
            method3.visibility = View.GONE
            walk4.visibility = View.GONE
        }

        if (selectedPath.path!!.subPath.size > 6) {
            val walkParam4 = walk4.layoutParams as LinearLayout.LayoutParams
            walkParam4.weight = selectedPath.path!!.subPath[6].sectionTime.toFloat()
            walk4.layoutParams = walkParam4
        } else {
            walk4.visibility = View.GONE
        }
    }

    private fun setPostButton(){
        act_schedule_tv_register.setOnClickListener {

            val scheName = findViewById<EditText>(R.id.act_schedule_et_name).text.toString()
            if (scheName.isEmpty()) {
                Toast.makeText(this, "내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                postSchedule(scheName)
            }
        }
    }

    private fun postSchedule(scheName: String){

        var jsonObject = JSONObject()
        jsonObject.put("scheduleName", scheName)
        jsonObject.put("scheduleStartTime", SimpleDateFormat("HH:mm").format(cal.time))
        jsonObject.put("scheduleStartDay", SimpleDateFormat("yyyy-MM-dd").format(cal.time))

        jsonObject.put("startAddress", startPlaceName)
        jsonObject.put("startLongitude", startPlaceX)
        jsonObject.put("startLatitude", startPlaceY)
        jsonObject.put("endAddress", endPlaceName)
        jsonObject.put("endLongitude", endPlaceX)
        jsonObject.put("endLatitude", endPlaceY)


        jsonObject.put("arriveCount", arriveCount)
        jsonObject.put("noticeMin", noticeMin)
        jsonObject.put("userIdx", 7)

        val gson = Gson()
        val path = gson.toJson(path!!)

        jsonObject.put("path", path)


        if (mon.isSelected) weekdays.add(0)
        if (tue.isSelected) weekdays.add(1)
        if (wed.isSelected) weekdays.add(2)
        if (thu.isSelected) weekdays.add(3)
        if (fri.isSelected) weekdays.add(4)
        if (sat.isSelected) weekdays.add(5)
        if (sun.isSelected) weekdays.add(6)
        jsonObject.put("weekdays", weekdays)

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        val callPostSchedule: Call<PostScheduleData> = EarlyBuddyServiceImpl.service.postSchedule(body)

        callPostSchedule.enqueue(object : Callback<PostScheduleData> {
            override fun onFailure(call: Call<PostScheduleData>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(call: Call<PostScheduleData>, response: Response<PostScheduleData>) {
                if (response.isSuccessful) {
                    Log.e("result is ", response.body().toString())
                    scheduleIdx = response.body()!!.data
                    Log.e("scheduleIdx", scheduleIdx.toString())

                    ScheduleDialogFragment(scheduleIdx) { finish() }.apply {
                        show(supportFragmentManager, null)
                        finish()
                    }
                }
                else{
                    Toast.makeText(this@ScheduleActivity, "네트워크를 확인해 주세요",Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}

