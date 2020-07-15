package com.lion.komvvm

import android.app.Application
import com.blankj.utilcode.util.LogUtils

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        LogUtils.getConfig()
            .setLogSwitch(BuildConfig.openLog)
            .setSingleTagSwitch(BuildConfig.singleTag)
    }
}