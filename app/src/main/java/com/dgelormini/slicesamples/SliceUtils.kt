package com.dgelormini.slicesamples

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.service.voice.VoiceInteractionService
import androidx.slice.SliceManager
import com.dgelormini.slicesamples.slices.MySliceProvider

internal object SliceUtils {
    /**
     * Grant slice permissions to the assistance in order to display slices without user input.
     *
     * Note: this is needed when using AndroidX SliceProvider.
     */
    fun grantAssistantPermissions(context: Context) {
        getAssistantPackage(context)
            ?.let { assistantPackage ->
                val sliceProviderUri = Uri.Builder()
                    .scheme(ContentResolver.SCHEME_CONTENT)
                    .authority(MySliceProvider.PROVIDER_AUTHORITY)
                    .build()

                SliceManager.getInstance(context)
                    .grantSlicePermission(assistantPackage, sliceProviderUri)
            }
    }

    /**
     * Find the assistant package name
     */
    private fun getAssistantPackage(context: Context): String? {
        val resolveInfoList = context.packageManager?.queryIntentServices(
            Intent(VoiceInteractionService.SERVICE_INTERFACE), 0
        )
        return resolveInfoList?.firstOrNull()?.serviceInfo?.packageName
    }
}
