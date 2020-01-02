package com.devaon.early_buddy_android.feature.place.select

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.PlaceSearch
import com.devaon.early_buddy_android.feature.place.search.text.PlaceSearchAdapter
import kotlinx.android.synthetic.main.activity_place_select.*
import kotlinx.android.synthetic.main.fragment_place_select.*


class PlaceDirectionsActivity : AppCompatActivity() {

    private lateinit var placeSelectAdapter: PlaceSearchAdapter
    private var placeDataList = ArrayList<PlaceSearch>()

    lateinit var nowFragment : Fragment
    val TAG : String = "PlaceDirectionsAct"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_select)

        // 글자 하나 바뀔 때마다 실행되는 EditText 메서
        act_place_search_route_et_engine_from.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                getData()
                Log.v(TAG, "글자 변경")
            }

        })

        act_place_search_route_tv_engine_from.setOnClickListener {
            changeFragment("select")
        }

        act_place_search_route_tv_engine_to.setOnClickListener {
            changeFragment("search")
        }

        //initPlaceSearchList()
    }

    fun getData(){

    }


    // Fragment Replace 하는 메서드
    fun changeFragment(frag : String) {

        Log.v("PlaceDetailAct", "프래그먼트 체인지")
        when(frag){
            "select" -> {
                Log.v("PlaceDetailAct", "프래그먼트 select로 체인지")

                nowFragment = PlaceSelectFragment()
            }
            "search" -> {
                Log.v("PlaceDetailAct", "프래그먼트 search로 체인지")
                nowFragment = PlaceSearchRouteFragment()
            }
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.act_place_select_fl_content, nowFragment)
        transaction.commit()
    }


    /*private fun initPlaceSearchList() {
        placeSelectAdapter = PlaceSelectAdapter(
            this
        ).apply {
            data = listOf(
                GetPlaceData(
                    "만수주공10단지아파트 1001동",
                    "인천 남동구 만수동 1043 만수주공10단지아파트 1001",
                    "장승남로 34"
                ),
                GetPlaceData(
                    "만수주공10단지아파트 1002동",
                    "인천 남동구 만수동 1043 만수주공10단지아파트 1002",
                    "장승남로 34"
                ),
                GetPlaceData(
                    "만수주공10단지아파트 1003동",
                    "인천 남동구 만수동 1043 만수주공10단지아파트 1003",
                    "장승남로 34"
                ),
                GetPlaceData(
                    "만수주공10단지아파트 1004동",
                    "인천 남동구 만수동 1043 만수주공10단지아파트 1004",
                    "장승남로 34"
                ),
                GetPlaceData(
                    "만수주공10단지아파트 1005동",
                    "인천 남동구 만수동 1043 만수주공10단지아파트 1005",
                    "장승남로 34"
                ),
                GetPlaceData(
                    "만수주공10단지아파트 1006동",
                    "인천 남동구 만수동 1043 만수주공10단지아파트 1006",
                    "장승남로 34"
                )
            )
        }
        //test용 데이터
        act_place_select_rv.adapter = placeSelectAdapter
    }*/

   /* private fun setData() {
        //데이터 받아오는 함수
        if (placeDataList.isNullOrEmpty()) {
            act_place_select_iv_bird.visibility = View.VISIBLE
            act_place_select_rv.visibility = View.GONE
        } else {
            act_place_select_iv_bird.visibility = View.GONE
            act_place_select_rv.visibility = View.VISIBLE

            //통신할 때 데이터 받아와야함  data =

        }
    }*/


}