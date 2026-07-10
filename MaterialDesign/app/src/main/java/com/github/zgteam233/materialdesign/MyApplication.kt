package com.github.zgteam233.materialdesign

import android.app.Application
import android.content.Context

//
// Copyright(c) 2026 ZGTeam233.
//
class MyApplication : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}