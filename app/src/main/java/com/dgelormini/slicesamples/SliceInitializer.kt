package com.dgelormini.slicesamples

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

private const val TAG = "SliceInitializer"

class SliceInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Log.i(TAG, "Granting URI permissions")

        SliceUtils.grantAssistantPermissions(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf()
    }
}