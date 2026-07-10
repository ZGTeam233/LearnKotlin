package com.github.zgteam233.activitytest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

//
// Copyright(c) 2026 ZGTeam233.
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