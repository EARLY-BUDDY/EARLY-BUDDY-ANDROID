package com.devaon.early_buddy_android.feature.schedule

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.dialog_fragment_schedule_pop_up.*

class ScheduleDialogFragment(
    val scheduleIdx : Int
) : DialogFragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_schedule_pop_up, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog_fragment_sche_pop_up_tv_check.setOnClickListener {
            var goToCheck = Intent(this@ScheduleDialogFragment.context, ScheduleCompleteActivity::class.java)
            goToCheck.putExtra("scheduleIdx", scheduleIdx)
            dismiss()
            startActivity(goToCheck)
        }
        dialog_fragment_sche_pop_up_tv_home.setOnClickListener {
            dismiss()
        }
    }
}