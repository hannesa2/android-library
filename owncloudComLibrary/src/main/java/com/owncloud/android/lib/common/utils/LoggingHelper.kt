package com.owncloud.android.lib.common.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import info.hannes.crashlytic.CrashlyticsTree
import info.hannes.logcat.BuildConfig
import info.hannes.timber.DebugTree
import info.hannes.timber.FileLoggingTree
import info.hannes.timber.fileLoggingTree
import timber.log.Timber
import java.io.File

object LoggingHelper {

    fun startLogging(directory: File, storagePath: String) {
        fileLoggingTree()?.let {
            Timber.forest().drop(Timber.forest().indexOf(it))
        }
        if (!directory.exists())
            directory.mkdirs()
        Timber.plant(FileLoggingTree(directory, filename = storagePath))
        Timber.plant(DebugTree())
        Timber.plant(CrashlyticsTree())

        FirebaseCrashlytics.getInstance().setCustomKey("VERSION_NAME", BuildConfig.VERSIONNAME)
        Timber.plant(CrashlyticsTree())
    }

    fun stopLogging() {
        fileLoggingTree()?.let {
            Timber.forest().drop(Timber.forest().indexOf(it))
        }
    }
}
