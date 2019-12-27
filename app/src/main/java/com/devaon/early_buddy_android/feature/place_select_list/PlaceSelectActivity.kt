package com.devaon.early_buddy_android.feature.place_select_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.GetPlaceData
import kotlinx.android.synthetic.main.activity_place_select.*


class PlaceSelectActivity : AppCompatActivity() {

    private lateinit var placeSelectAdapter: PlaceSelectAdapter
    private var placeDataList = ArrayList<GetPlaceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_select)

        initPlaceSearchList()
    }

    private fun initPlaceSearchList() {
        placeSelectAdapter = PlaceSelectAdapter(this).apply {
            data = listOf(
                GetPlaceData("만수주공10단지아파트 1001동", "인천 남동구 만수동 1043 만수주공10단지아파트 1001", "장승남로 34"),
                GetPlaceData("만수주공10단지아파트 1002동", "인천 남동구 만수동 1043 만수주공10단지아파트 1002", "장승남로 34"),
                GetPlaceData("만수주공10단지아파트 1003동", "인천 남동구 만수동 1043 만수주공10단지아파트 1003", "장승남로 34"),
                GetPlaceData("만수주공10단지아파트 1004동", "인천 남동구 만수동 1043 만수주공10단지아파트 1004", "장승남로 34"),
                GetPlaceData("만수주공10단지아파트 1005동", "인천 남동구 만수동 1043 만수주공10단지아파트 1005", "장승남로 34"),
                GetPlaceData("만수주공10단지아파트 1006동", "인천 남동구 만수동 1043 만수주공10단지아파트 1006", "장승남로 34")
            )
        }
        //test용 데이터
        act_place_select_rv.adapter = placeSelectAdapter
    }

    private fun setData() {
        //데이터 받아오는 함수
        if (placeDataList.isNullOrEmpty()) {
            act_place_select_iv_bird.visibility = View.VISIBLE
            act_place_select_rv.visibility = View.GONE
        } else {
            act_place_select_iv_bird.visibility = View.GONE
            act_place_select_rv.visibility = View.VISIBLE

            //통신할 때 데이터 받아와야함  data =

        }
    }
}