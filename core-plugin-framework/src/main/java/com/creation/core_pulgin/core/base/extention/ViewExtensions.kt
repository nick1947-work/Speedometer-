package com.creation.core_pulgin.core.base.extention

import android.view.View
import com.creation.core_pulgin.core.listeners.TouchAnimationListener

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }


inline fun View.setOnTouchAnimationListener(crossinline onClick: () -> Unit) {
    setOnTouchListener(object : TouchAnimationListener() {
        override fun onClick() {
            onClick()
        }
    })
}