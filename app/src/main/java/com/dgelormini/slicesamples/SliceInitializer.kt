package com.dgelormini.slicesamples

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

private const val TAG = "SliceInitializer"

class SliceInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Log.i(TAG, "Granting URI permissions")

        // TODO: This hangs and/or crashes on Android 9!
        SliceUtils.grantAssistantPermissions(context)

        Log.i(TAG, "Granted URI permissions successfully")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf()
    }
}