package com.github.zgteam233.activitytest

import android.app.Activity

//
// Created by wsnzg6 on ${DATE}.
// Copyright(c) $YEAR ZGTeam233.
//
object ActivityCollector {
    private val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }

}