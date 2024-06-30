package com.lesfeesdesoeufsbio.eggcounter.utils

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


class AppLifecycleObserver(private val activity : Activity) : LifecycleObserver {

    private var date = TimeHelper.getCurrentLocalDateTime().date

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {

        // reload view if the day change
        if(date != TimeHelper.getCurrentLocalDateTime().date){
            print("reload")
            activity.finish()
            activity.startActivity(activity.intent)
        }
    }

    companion object {
        val TAG: String = AppLifecycleObserver::class.java.name
    }
}