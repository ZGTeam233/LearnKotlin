package com.github.zgteam233.activitytest

import android.widget.Toast

//
// Copyright(c) 2026 ZGTeam233.
//
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApplication.context, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApplication.context, this, duration).show()
}
