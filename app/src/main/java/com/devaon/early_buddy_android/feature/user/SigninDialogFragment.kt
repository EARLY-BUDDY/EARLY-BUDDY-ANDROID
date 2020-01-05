package com.devaon.early_buddy_android.feature.user

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.devaon.early_buddy_android.R
import androidx.fragment.app.DialogFragment as DialogFragment1


class SigninDialogFragment : DialogFragment1(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.dialog_fragment_signin_complete,container,false)
        var btn = view.findViewById<TextView>(R.id.dialog_fragment_signin_btn)
        btn.setOnClickListener {
            dismiss()
        }
        return view
    }
    lateinit var listener : OnDialogDismissedListener

    fun setOnDialogDismissedListener(listener: OnDialogDismissedListener) {
        this.listener = listener
    }
    interface OnDialogDismissedListener {
        fun onDialogDismissed()
    }
    override fun dismiss() {
        listener.onDialogDismissed()
        super.dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}