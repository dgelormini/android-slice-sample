package com.dgelormini.slicesamples.slices

import android.app.PendingIntent
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.core.graphics.drawable.IconCompat
import androidx.slice.Slice
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.SliceAction
import androidx.slice.builders.header
import androidx.slice.builders.list
import com.dgelormini.slicesamples.R

abstract class BaseSlice(private val context: Context, private val sliceUri: Uri) {

    protected val handler = Handler(Looper.getMainLooper())

    fun refresh() {
        context.contentResolver.notifyChange(sliceUri, null)
    }

    abstract fun getSlice(): Slice

    fun createErrorSlice(): Slice {
        return ErrorSlice(context, sliceUri).getSlice()
    }

    fun createLoadingSlice(): Slice {
        return list(context, sliceUri, ListBuilder.INFINITY) {
            header {
                setTitle(
                    "Loading...",
                    true
                )
            }
        }
    }

    // TODO: Keep or kill? Can just return a Slice directly...
    class ErrorSlice(private val context: Context, private val sliceUri: Uri) :
        BaseSlice(context, sliceUri) {

        override fun getSlice() = list(context, sliceUri, ListBuilder.INFINITY) {
            header {
                title = "An error occurred"
                subtitle = "Make sure you're logged in and try again."
                primaryAction = createUnauthorizedSliceAction()
            }
            setIsError(true)
        }

        private fun createUnauthorizedSliceAction(): SliceAction {
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)

            return SliceAction.create(
                PendingIntent.getActivity(context, 0, intent, 0),
                IconCompat.createWithResource(context, R.drawable.ic_launcher_foreground),
                ListBuilder.SMALL_IMAGE,
                "Action title"
            )
        }
    }

    class Unknown(private val context: Context, private val sliceUri: Uri) :
        BaseSlice(context, sliceUri) {
        override fun getSlice(): Slice {
            return list(context, sliceUri, ListBuilder.INFINITY) {
                header {
                    title = "An unknown error occurred"
                    subtitle = "¯\\_(ツ)_/¯"
                }
                setIsError(true)
            }
        }
    }
}