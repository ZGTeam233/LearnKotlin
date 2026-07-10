package com.github.zgteam233.activitytest

import android.widget.Toast

//
// Created by wsnzg6 on ${DATE}.
// Copyright(c) $YEAR ZGTeam233.
//
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApplication.context, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApplication.context, this, duration).show()
}
