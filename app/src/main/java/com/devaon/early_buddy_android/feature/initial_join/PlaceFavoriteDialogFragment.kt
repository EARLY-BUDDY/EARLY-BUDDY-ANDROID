package com.devaon.early_buddy_android.feature.initial_join

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.dialog_fragment_place_favorite_icon_select.*
import androidx.fragment.app.DialogFragment as DialogFragment1

class PlaceFavoriteDialogFragment : DialogFragment1(){

    lateinit var home:ImageView
    lateinit var office:ImageView
    lateinit var school:ImageView
    lateinit var other:ImageView
    lateinit var cancel:ImageView
/*
    var flag:Boolean = true*/

    //icon idex 0: home, 1: office, 2: school, 3: other, default : 0
    var selectedIdx  = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.dialog_fragment_place_favorite_icon_select,container,false)
         home = view.findViewById<ImageView>(R.id.dialog_fragment_place_favorite_iv_home)
         office = view.findViewById<ImageView>(R.id.dialog_fragment_place_favorite_iv_office)
         school = view.findViewById<ImageView>(R.id.dialog_fragment_place_favorite_iv_school)
         other = view.findViewById<ImageView>(R.id.dialog_fragment_place_favorite_iv_other)
        cancel = view.findViewById<ImageView>(R.id.dialog_fragment_place_favorite_iv_cancel)

        selectIcon()

        var btn = view.findViewById<TextView>(R.id.dialog_fragment_place_favorite_tv_ok)
        btn.setOnClickListener {
            dismiss()
        }
        cancel.setOnClickListener {
            dismiss()
        }
        return view
    }
    lateinit var listener : OnDialogDismissedListener

    fun setOnDialogDismissedListener(listener: OnDialogDismissedListener) {
        this.listener = listener
    }
    interface OnDialogDismissedListener {
        fun onDialogDismissed(selectedIdx : Int)
    }
    override fun dismiss() {
        listener.onDialogDismissed(selectedIdx)
        super.dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun selectIcon(){
        home.setOnClickListener {
            Log.d("test", "home oncolick")
            if(!home.isSelected){
                Log.d("test", "home is selected")
                home.setSelected(true)
                office.setSelected(false)
                school.setSelected(false)
                other.setSelected(false)
            }
            selectedIdx = 0
        }

        office.setOnClickListener {
            if(!office.isSelected){
                office.setSelected(true)
                home.setSelected(false)
                school.setSelected(false)
                other.setSelected(false)
            }
            selectedIdx = 1
        }

        school.setOnClickListener {
            if(!school.isSelected){
                school.setSelected(true)
                office.setSelected(false)
                home.setSelected(false)
                other.setSelected(false)
            }
            selectedIdx = 2
        }

        other.setOnClickListener {
            if(!other.isSelected){
                other.setSelected(true)
                office.setSelected(false)
                school.setSelected(false)
                home.setSelected(false)
            }
            selectedIdx = 3
        }


    }
}