package com.github.zgteam233.activitytest

import android.app.Application
import android.content.Context

//
// Created by wsnzg6 on ${DATE}.
// Copyright(c) $YEAR ZGTeam233.
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