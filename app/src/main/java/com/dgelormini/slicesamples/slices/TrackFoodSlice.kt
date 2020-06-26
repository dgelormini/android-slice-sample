package com.dgelormini.slicesamples.slices

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.slice.Slice
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.header
import androidx.slice.builders.list
import kotlinx.coroutines.*
import kotlin.random.Random

private const val TAG = "TrackFoodSlice"

class TrackFoodSlice(private val context: Context, private val sliceUri: Uri) :
    BaseSlice(context, sliceUri) {

    private var currentState: SliceState? = null
/*        private set(value) {
            if(field != value) {
                refresh() // Refresh state when this state changes
            }
            field = value
        }*/

    init {
        Log.d(TAG, "Initialized slice with state: $currentState")
        fetchData()
    }

    fun fetchData() {
        CoroutineScope(Job()).launch {
            withContext(Dispatchers.IO) {
                delay(1500)
                currentState = if (Random.nextBoolean()) {
                    SliceState.Success("Success!")
                } else {
                    SliceState.Error
                }
                Log.d(TAG, "current state changed to: $currentState")
                refresh()
            }
        }
    }

    override fun isTerminal(): Boolean {
        return (currentState is SliceState.Success) || (currentState is SliceState.Error)
    }

    override fun getSlice(): Slice {
        Log.d(TAG, "getSlice() called, current state: $currentState")
        return when (currentState) {
            is SliceState.Success -> createSuccessSlice((currentState as SliceState.Success).data)
            is SliceState.Error -> createErrorSlice()
            else -> createLoadingSlice()
        }
    }

    private fun createSuccessSlice(data: String): Slice {
        return list(context, sliceUri, ListBuilder.INFINITY) {
            header {
                title = "Success!"
                subtitle = "Data: `$data`"
            }
        }
    }

    internal sealed class SliceState {
        object Loading : SliceState()
        object Error : SliceState()
        data class Success(val data: String) : SliceState()
    }
}
