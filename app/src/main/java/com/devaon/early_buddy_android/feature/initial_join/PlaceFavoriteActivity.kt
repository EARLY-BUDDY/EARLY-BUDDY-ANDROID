package com.devaon.early_buddy_android.feature.initial_join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.feature.place.search.text.PlaceSearchActivity
import com.devaon.early_buddy_android.feature.user.SigninActivity
import com.github.clans.fab.FloatingActionMenu
import kotlinx.android.synthetic.main.activity_place_favorite.*

class PlaceFavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_favorite)

        selectPlaceController()
        makeRegisterController()

    }


    private fun makeRegisterController() {
        act_place_favorite_tv_skip.setOnClickListener {
            val intent = Intent(this@PlaceFavoriteActivity, SetCompleteActivity::class.java)
            startActivity(intent)
        }
/*
        act_place_favorite_tv_skip.setOnClickListener {
            val intent = Intent(this@PlaceFavoriteActivity, SetCompleteActivity::class.java)
            startActivity(intent)
        }*/

    }


    private fun selectPlaceController() {

        act_place_favorite_cl_register_first.setOnClickListener {
            val intent = Intent(this@PlaceFavoriteActivity, PlaceSearchActivity::class.java)
            startActivity(intent)
        }

        act_place_favorite_cl_register_second.setOnClickListener {
            val intent = Intent(this@PlaceFavoriteActivity, PlaceSearchActivity::class.java)
            startActivity(intent)
        }

        act_place_favorite_cl_register_third.setOnClickListener {
            val intent = Intent(this@PlaceFavoriteActivity, PlaceSearchActivity::class.java)
            startActivity(intent)
        }

    }


    private fun selectIconController() {

        val placeFavoriteDialog = PlaceFavoriteDialogFragment()
        placeFavoriteDialog.setOnDialogDismissedListener(PlaceFavoriteDialogFragmentDismissListener)

        dialog_fragment_place_favorite_iv_select_one.setOnClickListener {
            placeFavoriteDialog.show(supportFragmentManager,"select_icon_first")
        }

        dialog_fragment_place_favorite_iv_select_two.setOnClickListener {
            placeFavoriteDialog.show(supportFragmentManager,"select_icon_second")
        }

        dialog_fragment_place_favorite_iv_select_three.setOnClickListener {
            placeFavoriteDialog.show(supportFragmentManager,"select_icon_third")
        }

    }

    var PlaceFavoriteDialogFragmentDismissListener = object : PlaceFavoriteDialogFragment.OnDialogDismissedListener {
        override fun onDialogDismissed() {
            //dismiss일때 선택한 icon, 장소 PlaceFavoriteActivity 여기에 들어가도록
            val intent = Intent(this@PlaceFavoriteActivity, SigninActivity::class.java)
            startActivity(intent)
        }
    }

}
