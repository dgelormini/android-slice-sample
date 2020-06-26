package com.dgelormini.slicesamples

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SliceUtils.grantAssistantPermissions(this)
    }
}
