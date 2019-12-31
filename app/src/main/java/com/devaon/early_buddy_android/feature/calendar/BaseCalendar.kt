package com.devaon.early_buddy_android.feature.calendar

import com.devaon.early_buddy_android.data.calendar.Date
import java.text.SimpleDateFormat
import java.util.*


class BaseCalendar(timeByMillis: Long) {

    companion object {
        const val DAYS_OF_WEEK = 7
        const val LOW_OF_CALENDAR = 5
    }

    val calendar = Calendar.getInstance()

    var prevMonthTailOffset = 0
    var nextMonthHeadOffset = 0
    var currentMonthMaxDate = 0

    var year = 0
    var month = 0

    var todayYear = 0
    var todayMonth =0
    var today = 0

    val yearFormat = SimpleDateFormat("yyyy", Locale.KOREA)
    val monthFormat = SimpleDateFormat("MM", Locale.KOREA)
    val dayFormat = SimpleDateFormat("dd", Locale.KOREA)

    var data = arrayListOf<Date>()

    init {
        calendar.time = Date(timeByMillis)

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)

        todayYear = yearFormat.format(Date()).toInt()
        todayMonth = monthFormat.format(Date()).toInt()
        today = dayFormat.format(Date()).toInt()
    }

    fun initBaseCalendar(refreshCallback: (Calendar) -> Unit){
        makeMonthDate(refreshCallback)
    }


    // 전 달로 이동
    fun changeToPrevMonth(refreshCallback: (Calendar) -> Unit) {
        if(month == 0){
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1)
            calendar.set(Calendar.MONTH, Calendar.DECEMBER)
        }else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        }
        makeMonthDate(refreshCallback)
    }

    // 다음 달로 이동
    fun changeToNextMonth(refreshCallback: (Calendar) -> Unit) {
        if(month == Calendar.DECEMBER){
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
            calendar.set(Calendar.MONTH, 0)
        }else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
        }
        makeMonthDate(refreshCallback)
    }


    // 달력 만들기
    private fun makeMonthDate(refreshCallback: (Calendar) -> Unit){
        data.clear()

        calendar.set(Calendar.DATE, 1)
        currentMonthMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        prevMonthTailOffset = calendar.get(Calendar.DAY_OF_WEEK) - 1

        makePrevMonthTail(calendar.clone() as Calendar)
        makeCurrentMonth(calendar)

        nextMonthHeadOffset = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevMonthTailOffset + currentMonthMaxDate)
        makeNextMonthHead()

        refreshCallback(calendar)
    }

    // 이번 달 달력에 전 달 날짜들 추가
    private fun makePrevMonthTail(calendar: Calendar) {
        calendar.set(Calendar.MONTH, month - 1)
        val maxDate = calendar.getActualMaximum(Calendar.DATE)
        var maxOffsetDate = maxDate - prevMonthTailOffset

        if(month == Calendar.JANUARY) {
            for (i in 1..prevMonthTailOffset) {
                data.add(Date(
                    (year - 1).toString(),
                    (Calendar.DECEMBER).toString(),
                    (++maxOffsetDate).toString(),
                    false,
                    false
                ))
            }
        }else{
            for (i in 1..prevMonthTailOffset) {
                data.add(
                    Date(
                    year.toString(),
                    month.toString(),
                    (++maxOffsetDate).toString(),
                    false,
                    false
                )
                )
            }
        }
    }

    // 이번 달 달력에 다음 달 날짜들 추가
    private fun makeNextMonthHead() {
        var date = 1


        if(month == Calendar.DECEMBER) {
            for (i in 1..nextMonthHeadOffset) {
                data.add(Date(
                    (year + 1).toString(),
                    (Calendar.JANUARY).toString(),
                    (date++).toString(),
                    false,
                    false
                ))
            }
        }else{
            for (i in 1..nextMonthHeadOffset) {
                data.add(Date(
                    year.toString(),
                    month.toString(),
                    (date++).toString(),
                    false,
                    false
                ))
            }
        }
    }

    // 이번 달 날짜들 추가
    private fun makeCurrentMonth(calendar: Calendar) {

        for (i in 1..calendar.getActualMaximum(Calendar.DATE)) {
            if(year == todayYear && month == todayMonth-1 && today == i){
                data.add(
                    Date(
                        year.toString(),
                        month.toString(),
                        i.toString(),
                        true,
                        false
                    )
                )
            }else{
                data.add(
                    Date(
                        year.toString(),
                        month.toString(),
                        i.toString(),
                        false,
                        false
                    )
                )
            }
        }

    }
}