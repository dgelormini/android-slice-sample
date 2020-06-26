package com.dgelormini.slicesamples.slices

import android.net.Uri
import android.util.Log
import androidx.slice.Slice
import androidx.slice.SliceProvider


class MySliceProvider : SliceProvider() {
    companion object {
        const val PROVIDER_AUTHORITY = "com.dgelormini.slicesamples.slices.provider"
        private const val TAG = "MySliceProvider"
    }

    private val slices = mutableMapOf<Uri, BaseSlice>()

    override fun onCreateSliceProvider(): Boolean {
        return true
    }

    override fun onBindSlice(sliceUri: Uri): Slice {
        Log.d(TAG, "onBindSlice() called for $sliceUri")
        val lastBaseSlice = slices.getOrPut(sliceUri) { createSlice(sliceUri) }
        val slice = lastBaseSlice.getSlice()

        if (lastBaseSlice.isTerminal()) {
            slices.remove(sliceUri)
        }

        return slice
    }

    private fun createSlice(sliceUri: Uri): BaseSlice {
        val context = requireNotNull(context) {
            "SliceProvider $this not attached to a context."
        }

        return if (sliceUri.path?.contains("/track-food") == true) {
            TrackFoodSlice(context, sliceUri)
        } else {
            BaseSlice.Unknown(context, sliceUri)
        }
    }
}