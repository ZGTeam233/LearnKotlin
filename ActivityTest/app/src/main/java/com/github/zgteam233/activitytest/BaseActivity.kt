package com.github.zgteam233.activitytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

//
// Created by wsnzg6 on ${DATE}.
// Copyright(c) $YEAR ZGTeam233.
//
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("BaseActivity", javaClass.simpleName)
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}