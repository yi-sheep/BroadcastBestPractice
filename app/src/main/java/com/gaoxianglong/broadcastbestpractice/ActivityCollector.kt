package com.gaoxianglong.broadcastbestpractice

import android.app.Activity

/**
 * 用于关闭全部activity
 */
object ActivityCollector {
    private val activities = ArrayList<Activity>() // 当前打开的activity列表

    // 打开一个activity时添加到列表中
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }
    // 当关闭一个activity时在列表中将他移除
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }
}