package com.devaon.early_buddy_android.util.view

import android.view.View
import com.devaon.early_buddy_android.util.SafeClickListener

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}