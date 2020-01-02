package com.devaon.early_buddy_android.util.view

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.EarlyBuddyApplication

class WrapContentLinearLayoutManager : LinearLayoutManager(
    EarlyBuddyApplication.getGlobalApplicationContext(), RecyclerView.VERTICAL, false) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("TAG", "meet a IOOBE in RecyclerView")
        }

    }
}