package com.devaon.early_buddy_android.data.place.calendar

data class Date(
    var year : String,
    var month : String,
    var date : String,
    var isToDay: Boolean,
    var hasSchedule : Boolean = false
)