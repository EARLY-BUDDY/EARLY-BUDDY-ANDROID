package com.devaon.early_buddy_android.feature.place.search.text

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.PlaceResponse
import com.devaon.early_buddy_android.data.place.PlaceSearch
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import kotlinx.android.synthetic.main.activity_place_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceSearchActivity : AppCompatActivity() {

    private lateinit var placeSearchAdapter: PlaceSearchAdapter
    private var placeDataList = ArrayList<PlaceSearch>()

    private lateinit var searchEdit: EditText
    private  var clearButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_search)

        //initPlaceSearchList()
        clearText()
        setRv()
        //getPlaceSearch()
        //goToSearchStartPlaceActivity()
        act_place_search_iv_search.setOnClickListener {
           /* val intent = Intent(this@PlaceSearchActivity, PlaceSearchActivity::class.java)
            startActivity(intent)*/
            getPlaceSearch()
        }

    }

    private fun setRv(){
        placeSearchAdapter = PlaceSearchAdapter(this)
        act_place_search_rv.adapter = placeSearchAdapter
        act_place_search_rv.layoutManager = LinearLayoutManager(this)
    }

    /*private fun setData() {
        //데이버 받아오는 함수
        if (placeDataList.isNullOrEmpty()) {
            act_place_search_iv_bird.visibility = View.VISIBLE
            act_place_search_rv.visibility = View.GONE
        } else {
            act_place_search_iv_bird.visibility = View.GONE
            act_place_search_rv.visibility = View.VISIBLE

            //통신할 때 데이터 받아와야함  data =

        }
    }
*/

    private fun clearText(){
        act_place_search_iv_delete.setOnClickListener {
            act_place_search_et_search.text.clear()
        }
    }

    /*private fun getKeywordSearch(keyword: String) {
        subscription = SearchService.restAPI().keywordSearch(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d("keywordResult", result.documents[0].place_name)
                },
                { err ->
                    Log.e("Error User",err.toString())
                }
            )
    }
*/

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
                    val place = response.body()!!.data

                    placeSearchAdapter.replaceAll(place)
                    placeSearchAdapter.notifyDataSetChanged()
                }
            }
        })
    }



}