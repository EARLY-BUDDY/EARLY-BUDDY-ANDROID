package com.devaon.early_buddy_android.feature.place.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devaon.early_buddy_android.R


class PlaceSelectFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_place_select_department,container,false)


        return v
    }

    /*fun addData {

    }*/
}