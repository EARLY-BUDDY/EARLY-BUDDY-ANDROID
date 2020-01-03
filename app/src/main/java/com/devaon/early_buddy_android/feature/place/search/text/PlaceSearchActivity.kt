package com.devaon.early_buddy_android.feature.place.search.text

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.PlaceResponse
import com.devaon.early_buddy_android.data.place.PlaceSearch
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_place_favorite.*
import kotlinx.android.synthetic.main.activity_place_search.*
import kotlinx.android.synthetic.main.activity_place_select_direction.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceSearchActivity : AppCompatActivity() {

    private lateinit var placeSearchAdapter: PlaceSearchAdapter
    //lateinit var placeResponse: ArrayList<PlaceSearch>

     var placeNameString: String =""
     var xDouble : Double = 0.0
     var yDouble : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_search)

        clearText()
        setRv()

        act_place_search_et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //통신
                getPlaceSearch()
                Log.d("testtest", "onTextChanged")
            }
        })
    }

    private fun setRv(){
        placeSearchAdapter = PlaceSearchAdapter(this)
        placeSearchAdapter.setOnPlaceClickListener(onPlaceClickListener)
        act_place_search_rv.adapter = placeSearchAdapter
        act_place_search_rv.layoutManager = LinearLayoutManager(this)
    }


    private fun clearText(){
        act_place_search_iv_delete.setOnClickListener {
            act_place_search_et_search.text.clear()
        }
    }


    private fun getPlaceSearch() {
        val place = act_place_search_et_search.text.toString()


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
                    var placeResponse = response.body()!!.data




                    /*PlaceFavoriteActivity.placeObject.placeName = placeNameString
                    PlaceFavoriteActivity.placeObject.x = xDouble
                    PlaceFavoriteActivity.placeObject.y = yDouble  */

                    placeSearchAdapter.replaceAll(placeResponse)
                    placeSearchAdapter.notifyDataSetChanged()
                }
            }

        })
       /* placeNameString = placeResponse[0].placeName
        xDouble = placeResponse[0].x
        yDouble = placeResponse[0].y*/
    }

    var onPlaceClickListener
            = object : PlaceSearchAdapter.onPlaceClickListener {
        override fun onItemClick(placeName: String, x: Double, y: Double) {
            var firstSearch : Int = intent.getIntExtra("firstSearch", -1)
            var secondSearch : Int= intent.getIntExtra("secondSearch", -1)
            var thirdSearch : Int = intent.getIntExtra("thirdSearch", -1)

            if(firstSearch == 0){
                PlaceFavoriteActivity.placeObject.firstFavoriteName = placeName
                PlaceFavoriteActivity.placeObject.firstX = x
                PlaceFavoriteActivity.placeObject.firstY = y
                PlaceFavoriteActivity.placeObject.checkNum = 0
            }else if(secondSearch == 1){
                PlaceFavoriteActivity.placeObject.secondFavoriteName = placeName
                PlaceFavoriteActivity.placeObject.secondX = x
                PlaceFavoriteActivity.placeObject.secondY = y
                PlaceFavoriteActivity.placeObject.checkNum = 1
            }else if(thirdSearch == 2){
                PlaceFavoriteActivity.placeObject.thirdFavoriteName = placeName
                PlaceFavoriteActivity.placeObject.thirdX = x
                PlaceFavoriteActivity.placeObject.thirdY = y
                PlaceFavoriteActivity.placeObject.checkNum = 2
            }

            PlaceFavoriteActivity.placeObject.placeName = placeNameString
            PlaceFavoriteActivity.placeObject.x = xDouble
            PlaceFavoriteActivity.placeObject.y = yDouble

            Log.d("test", "PlaceSearchActivity - onPlaceClickListener 의 placeNameString : " + placeNameString)
            Log.d("test", "PlaceSearchActivity - onPlaceClickListener 의 PlaceFavoriteActivity.placeObject.placeName : " + PlaceFavoriteActivity.placeObject.placeName)
            Log.d("test", "PlaceSearchActivity - onPlaceClickListener 의 xDouble : " + xDouble)
            Log.d("test", "PlaceSearchActivity - onPlaceClickListener 의 PlaceFavoriteActivity.placeObject.x : " + PlaceFavoriteActivity.placeObject.x)
            Log.d("test", "PlaceSearchActivity - onPlaceClickListener 의 yDouble : " + yDouble)
            Log.d("test", "PlaceSearchActivity - onPlaceClickListener 의 PlaceFavoriteActivity.placeObject.y : " + PlaceFavoriteActivity.placeObject.y)

            placeNameString = placeName
            xDouble = x
            yDouble = y

            finish()
        }
    }



}