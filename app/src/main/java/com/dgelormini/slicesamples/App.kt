package com.dgelormini.slicesamples

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // TODO: Below works if we call it here
        // SliceUtils.grantAssistantPermissions(this)
    }
}
