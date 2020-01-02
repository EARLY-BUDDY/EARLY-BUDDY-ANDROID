package com.devaon.early_buddy_android.feature.place.search.text

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.PlaceResponse
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_place_search.*
import kotlinx.android.synthetic.main.activity_place_select_direction.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlaceDirectionsActivity : AppCompatActivity() {
    private lateinit var placeSearchAdapter: PlaceSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_select_direction)

        clearText()
        setRv()

        act_place_search_et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /*getData()
                Log.v(TAG, "글자 변경")*/

                //통신
                getPlaceSearch()
                Log.d("testtest", "onTextChanged")

            }

        })

    }

    private fun setRv(){
        placeSearchAdapter = PlaceSearchAdapter(this)
       // placeSearchAdapter.setOnPlaceClickListener(onPlaceClickListener)
        act_place_select_direction_rv.adapter = placeSearchAdapter
        act_place_select_direction_rv.layoutManager = LinearLayoutManager(this)
    }


    /*private fun setData() {
        //데이터 받아오는 함수
        if (placeDataList.isNullOrEmpty()) {
            act_place_search_iv_bird.visibility = View.VISIBLE
            act_place_search_rv.visibility = View.GONE
        } else {
            act_place_search_iv_bird.visibility = View.GONE
            act_place_search_rv.visibility = View.VISIBLE

            //통신할 때 데이터 받아와야함  data =

        }
    }*/

    private fun clearText(){
        act_place_select_direction_iv_delete.setOnClickListener {
            act_place_select_direction_et.text.clear()
        }
    }


    private fun getPlaceSearch() {
        Log.d("testtest", "getPlaceSearch")
        val place = act_place_select_direction_et.text.toString()

        val callPlace: Call<PlaceResponse> = EarlyBuddyServiceImpl.service.getSearchAddress(
            place
        )

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

    var onPlaceClickListener
            = object : PlaceSearchAdapter.onPlaceClickListener {
        override fun onItemClick(placeName: String, x: Double, y: Double) {


           /* if(PlaceFavoriteActivity.placeObject.firstFavoriteName == ""){
                PlaceFavoriteActivity.placeObject.firstFavoriteName = placeName
                PlaceFavoriteActivity.placeObject.firstX = x
                PlaceFavoriteActivity.placeObject.firstY = y
            }else if(PlaceFavoriteActivity.placeObject.secondFavoriteName == ""){
                PlaceFavoriteActivity.placeObject.secondFavoriteName = placeName
                PlaceFavoriteActivity.placeObject.secondX = x
                PlaceFavoriteActivity.placeObject.secondY = y
            }else if(PlaceFavoriteActivity.placeObject.thirdFavoriteName == ""){
                PlaceFavoriteActivity.placeObject.thirdFavoriteName = placeName
                PlaceFavoriteActivity.placeObject.thirdX = x
                PlaceFavoriteActivity.placeObject.thirdY = y
            }*/
            finish()
        }
    }


}