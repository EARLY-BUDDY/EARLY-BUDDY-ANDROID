package com.devaon.early_buddy_android.feature.place.search.text

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.PlaceResponse
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.endPlaceName
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.endPlaceX
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.endPlaceY
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.startPlaceName
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.startPlaceX
import com.devaon.early_buddy_android.feature.schedule.ScheduleActivity.schedulePlace.startPlaceY
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_place_select_direction.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlaceDirectionsActivity : AppCompatActivity() {

    private lateinit var placeSearchAdapter: PlaceSearchAdapter
    var flag = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_select_direction)

        clearText()
        setRv()
        setButton()
        showKeyboard()

        flag = intent.getStringExtra("click")

        if(flag == "from"){
            act_place_select_direction_et.text.clear()
            placeSearchAdapter.clearAll()
            placeSearchAdapter.notifyDataSetChanged()
            showKeyboard()
        }else{
            act_place_select_start_tv_search_start.text = "도착 : "
            act_place_select_direction_et.text.clear()
            placeSearchAdapter.clearAll()
            placeSearchAdapter.notifyDataSetChanged()
            showKeyboard()
        }
    }

    private fun setRv() {
        placeSearchAdapter = PlaceSearchAdapter(this)
        placeSearchAdapter.setOnPlaceClickListener(onPlaceClickListener)
        act_place_select_direction_rv.adapter = placeSearchAdapter
        act_place_select_direction_rv.layoutManager = LinearLayoutManager(this)
    }

    private fun setButton() {
        act_place_select_start_iv_back.setOnClickListener {
            hideKeyboard()
            finish()
        }

        act_place_select_direction_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                getPlaceSearch() //통신
            }

        })

        act_place_select_start_ns.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            hideKeyboard()
        }
    }

    private fun clearText() {
        act_place_select_direction_iv_delete.setOnClickListener {
            act_place_select_direction_et.text.clear()
        }
    }

    private fun getPlaceSearch() {
        val place = act_place_select_direction_et.text.toString()

        val callPlace: Call<PlaceResponse> = EarlyBuddyServiceImpl.service.getSearchAddress(place)

        callPlace.enqueue(object : Callback<PlaceResponse> {
            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                if (response.isSuccessful) {
                    Log.e("result is ", response.body().toString())
                    val place = response.body()!!.data

                    placeSearchAdapter.replaceAll(place)
                    placeSearchAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    var onPlaceClickListener = object : PlaceSearchAdapter.onPlaceClickListener {
        override fun onItemClick(placeName: String, x: Double, y: Double) {

            // todo: flag로 어떤 text 타고 들어온 건지 검사해서 처리!

            if (startPlaceName == "") {
                startPlaceName = placeName
                startPlaceX = x
                startPlaceY = y

                if (endPlaceName == "") {
                    act_place_select_start_tv_search_start.text = "도착 : "
                    act_place_select_direction_et.text.clear()
                    placeSearchAdapter.clearAll()
                    placeSearchAdapter.notifyDataSetChanged()
                    showKeyboard()
                }

            } else if (endPlaceName == "") {
                endPlaceName = placeName
                endPlaceX = x
                endPlaceY = y
                finish()
                hideKeyboard()
            } else {
                Log.e("token is ", flag.toString())
                if (flag == "from") {
                    startPlaceName = placeName
                    startPlaceX = x
                    startPlaceY = y
                    finish()
                    hideKeyboard()
                } else {
                    endPlaceName = placeName
                    endPlaceX = x
                    endPlaceY = y
                    finish()
                    hideKeyboard()
                }
            }
        }
    }

    private fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }


}