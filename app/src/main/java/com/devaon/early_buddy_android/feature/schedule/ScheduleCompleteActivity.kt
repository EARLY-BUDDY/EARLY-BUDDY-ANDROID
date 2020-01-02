package com.devaon.early_buddy_android.feature.schedule

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devaon.early_buddy_android.R

class ScheduleCompleteActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_complete)

        val scheduleIdx = intent.getIntExtra("scheduleIdx", 0)

        Log.e("schedule", scheduleIdx.toString())
    }
}