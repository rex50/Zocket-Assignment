package com.rex50.zocketassignment

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.pixplicity.easyprefs.library.Prefs
import com.rex50.zocketassignment.enums.Interval
import com.rex50.zocketassignment.workers.UpdatePageDetailsWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ZocketAssignmentApp: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        Prefs.Builder()
            .setContext(this)
            .setMode(MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        checkIfWorkerIsScheduled()
    }

    private fun checkIfWorkerIsScheduled() {
        UpdatePageDetailsWorker.schedule(this, Interval.TWELVE_HOURS)
    }

}