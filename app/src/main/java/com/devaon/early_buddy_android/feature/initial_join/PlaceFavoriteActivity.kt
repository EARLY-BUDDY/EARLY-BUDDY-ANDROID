package com.devaon.early_buddy_android.feature.initial_join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.login.Login
import com.devaon.early_buddy_android.data.place.FavoritePlace
import com.devaon.early_buddy_android.data.place.FavoritePlaceResponse
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.firstCategory
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.firstFavoriteName
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.firstX
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.firstY
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.secondCategory
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.secondFavoriteName
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.secondX
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.thirdCategory
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.thirdFavoriteName
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.thirdX
import com.devaon.early_buddy_android.feature.initial_join.PlaceFavoriteActivity.placeObject.thirdY
import com.devaon.early_buddy_android.feature.place.search.text.PlaceSearchActivity
import com.devaon.early_buddy_android.feature.user.SigninActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.github.clans.fab.FloatingActionMenu
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_place_favorite.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceFavoriteActivity : AppCompatActivity() {

    object placeObject{
        var firstFavoriteName : String = ""
        var secondFavoriteName : String = ""
        var thirdFavoriteName : String = ""
        var firstCategory = -1
        var secondCategory = -1
        var thirdCategory = -1

        var firstX : Double = 0.0
        var secondX: Double = 0.0
        var thirdX : Double = 0.0
        var firstY: Double = 0.0
        var secondY : Double = 0.0
        var thirdY: Double = 0.0

        var checkNum : Int = -1

        var placeName : String = ""
        var x : Double = 0.0
        var y : Double = 0.0


    }

    //아이콘 넘버
    var favoritePlaceNum = 0

    //장소검색 넘버
    var searchPlaceNum = 0


    var favoriteArr = arrayListOf<FavoritePlace>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_favorite)

        init()
    }


    override fun onResume() {
        super.onResume()

        if(placeObject.checkNum == 0){
            act_place_favorite_tv_first.text = placeObject.firstFavoriteName
            
            favoriteArr.add(FavoritePlace(firstFavoriteName, firstCategory, firstX, firstY))
        }else if(placeObject.checkNum == 1){
            act_place_favorite_tv_second.text = placeObject.secondFavoriteName
            favoriteArr.add(FavoritePlace(secondFavoriteName, secondCategory, secondX, secondX))
        }else if(placeObject.checkNum == 2){
            act_place_favorite_tv_third.text = placeObject.thirdFavoriteName
            favoriteArr.add(FavoritePlace(thirdFavoriteName, thirdCategory, thirdX, thirdY))
        }
    }

    private fun init(){
        selectIconController()
        selectPlaceController()
        makeRegisterController()
    }

    private fun makeRegisterController() {
        act_place_favorite_tv_skip.setOnClickListener {
            val intent = Intent(this@PlaceFavoriteActivity, SetCompleteActivity::class.java)
            startActivity(intent)
            finish()
        }

        act_place_favorite_tv.setOnClickListener {
            //자주가는 장소 등록 통신!
            postFavoroteData(favoriteArr)


            val intent = Intent(this@PlaceFavoriteActivity, SetCompleteActivity::class.java)
            startActivity(intent)
        }


    }


    private fun selectPlaceController() {
        act_place_favorite_cl_register_first.setOnClickListener {
            /*if(placeObject.firstFavoriteName != ""){
                placeObject.firstFavoriteName = ""
            }*/
            val intent = Intent(this@PlaceFavoriteActivity, PlaceSearchActivity::class.java)
            searchPlaceNum = 0
            intent.putExtra("firstSearch", 0)
            startActivity(intent)
        }

        act_place_favorite_cl_register_second.setOnClickListener {
            /*if(placeObject.secondFavoriteName != ""){
                placeObject.secondFavoriteName = ""
            }*/
            val intent = Intent(this@PlaceFavoriteActivity, PlaceSearchActivity::class.java)
            searchPlaceNum = 1
            intent.putExtra("secondSearch", 1)
            startActivity(intent)
        }

        act_place_favorite_cl_register_third.setOnClickListener {
            /*if(placeObject.thirdFavoriteName != ""){
                placeObject.thirdFavoriteName = ""
            }*/
            val intent = Intent(this@PlaceFavoriteActivity, PlaceSearchActivity::class.java)
            searchPlaceNum = 2
            intent.putExtra("thirdSearch", 2)
            startActivity(intent)
        }

    }


    private fun selectIconController() {

        val placeFavoriteDialog = PlaceFavoriteDialogFragment()
        placeFavoriteDialog.setOnDialogDismissedListener(PlaceFavoriteDialogFragmentDismissListener)

        act_place_favorite_iv_select_one.setOnClickListener {
            favoritePlaceNum = 0
            placeFavoriteDialog.show(supportFragmentManager,"select_icon_first")
        }

        act_place_favorite_iv_select_two.setOnClickListener {
            favoritePlaceNum = 1
            placeFavoriteDialog.show(supportFragmentManager,"select_icon_second")
        }

        act_place_favorite_iv_select_three.setOnClickListener {
            favoritePlaceNum = 2
            placeFavoriteDialog.show(supportFragmentManager,"select_icon_third")
        }

    }


    var PlaceFavoriteDialogFragmentDismissListener = object : PlaceFavoriteDialogFragment.OnDialogDismissedListener {
        override fun onDialogDismissed(selectedIdx : Int) {
            //dismiss일때 선택한 icon, 장소 PlaceFavoriteActivity 여기에 들어가도록
            /*val intent = Intent(this@PlaceFavoriteActivity, SigninActivity::class.java)
            startActivity(intent)*/


            //favoritePlaceNum 첫번쨰, 두번쨰, 세번째
            if(favoritePlaceNum == 0){
                when(selectedIdx){
                    0 ->  act_place_favorite_iv_select_one.setImageResource(R.drawable.ic_home_selected)
                    1 -> act_place_favorite_iv_select_one.setImageResource(R.drawable.ic_office_selected)
                    2 ->  act_place_favorite_iv_select_one.setImageResource(R.drawable.ic_school_selected)
                    3 ->  act_place_favorite_iv_select_one.setImageResource(R.drawable.ic_other_selected)

                }

                firstCategory = selectedIdx

            } else if(favoritePlaceNum == 1){
                when(selectedIdx){
                    0 ->  act_place_favorite_iv_select_two.setImageResource(R.drawable.ic_home_selected)
                    1 -> act_place_favorite_iv_select_two.setImageResource(R.drawable.ic_office_selected)
                    2 ->  act_place_favorite_iv_select_two.setImageResource(R.drawable.ic_school_selected)
                    3 ->  act_place_favorite_iv_select_two.setImageResource(R.drawable.ic_other_selected)

                }

                secondCategory = selectedIdx
            }else if(favoritePlaceNum == 2){
                when(selectedIdx){
                    0 ->  act_place_favorite_iv_select_three.setImageResource(R.drawable.ic_home_selected)
                    1 -> act_place_favorite_iv_select_three.setImageResource(R.drawable.ic_office_selected)
                    2 ->  act_place_favorite_iv_select_three.setImageResource(R.drawable.ic_school_selected)
                    3 ->  act_place_favorite_iv_select_three.setImageResource(R.drawable.ic_other_selected)
                }
                thirdCategory = selectedIdx
            }

        }
    }


    private fun postFavoroteData(array: ArrayList<FavoritePlace>) {

        var jsonArray = JSONArray()

        var jsons = ArrayList<JSONObject>()

        for(i in 0.. array.size-1){
            jsons[i].put("favoriteInfo", array[i].favoriteInfo)
            jsons[i].put("favoriteCategory", array[i].favoriteCategory)
            jsons[i].put("favoriteLongitude", array[i].favoriteLongitude)
            jsons[i].put("favoriteLatitude", array[i].favoriteLatitude)

        }

        val body = JsonParser().parse(jsons.toString()) as JsonObject

        val callFavoritePlaceResponse: Call<FavoritePlaceResponse> = EarlyBuddyServiceImpl.service.postFavoritePlace(
            Login.getToken(this@PlaceFavoriteActivity), body
        )

        callFavoritePlaceResponse.enqueue(object : Callback<FavoritePlaceResponse> {
            override fun onFailure(call: Call<FavoritePlaceResponse>, t: Throwable) {
                Log.e("set favorite place error is ", t.toString())
            }

            override fun onResponse(call: Call<FavoritePlaceResponse>, response: Response<FavoritePlaceResponse>) {
                if (response.isSuccessful) {
                    Log.e("set nickname result is ", response.body().toString())
                    val favorite = response.body()!!

                    Log.e("response message", response.message())
                    /*if(favorite.favoriteArr.) {
                        Log.e("nickname is null", "!")
                    }else {

                    }*/
                }else{
                    Log.e("PlaceFavorite response error", response.message() )
                }
            }
        })
    }

}
