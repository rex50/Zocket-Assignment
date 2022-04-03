package com.rex50.zocketassignment.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.rex50.zocketassignment.R
import com.rex50.zocketassignment.data.models.PageData
import com.rex50.zocketassignment.data.repos.meta.page.PagesRepo
import com.rex50.zocketassignment.enums.Interval
import com.rex50.zocketassignment.utils.Result.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

@HiltWorker
class UpdatePageDetailsWorker
@AssistedInject
constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val pagesRepo: PagesRepo
): CoroutineWorker(appContext, params) {

    companion object {
        private const val TAG = "UpdatePageDetailsWorker"
        const val UPDATE_PAGE_DETAILS_PROGRESS = "updatePageDetailsProgress"
        const val DEFAULT_NOTIFICATION_ID = "7777777"
        const val DEFAULT_NOTIFICATION_CHANNEL_NAME = "Page syncing notifications"
        const val DEFAULT_NOTIFICATION_TITLE = "Syncing"
        const val DEFAULT_NOTIFICATION_BODY = "Preparing..."
        const val TAG_UPDATE_NOW = "updateNow"
        const val TAG_SCHEDULED = "scheduled"

        @JvmStatic
        fun updateNow(
            context: Context,
            onRequestCreated: ((OneTimeWorkRequest) -> Unit)? = null
        ): UUID {

            val request = OneTimeWorkRequestBuilder<UpdatePageDetailsWorker>()
                .addTag(TAG_UPDATE_NOW)
                .build()

            onRequestCreated?.invoke(request)

            WorkManager.getInstance(context.applicationContext)
                .enqueue(request)

            return request.id
        }

        @JvmStatic
        fun schedule(
            context: Context,
            interval: Interval,
            workPolicy: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP
        ) {
            val request = PeriodicWorkRequestBuilder<UpdatePageDetailsWorker>(
                interval.interval,
                interval.unit
            ).setConstraints(getConstraints())
                .addTag(TAG_SCHEDULED)
                .build()

            //Can store requestId for future usage
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    TAG_SCHEDULED,
                    workPolicy,
                    request
                )
        }

        private fun getConstraints() = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

    }

    private lateinit var notificationBuilder: NotificationCompat.Builder

    private val notificationManager by lazy {
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
    }

    override suspend fun doWork(): Result {
        runCatching {

            // Get selected page details
            val pageData = getSelectedPageData()
            if(pageData == null) {
                Log.e(TAG, "No page is selected yet")
                return Result.failure(errorData("No page is selected yet"))
            }

            createNotification()
            updateNotification("Getting latest page details...")
            val updatedPageData = updateSelectedPageData(pageData)
            if(updatedPageData == null) {
                updateNotification("No page is selected yet")
                return Result.failure(errorData("Problem while getting updated page data"))
            }

            // Sync page details
            updateNotification("Syncing page details...")
            val syncResult = sendDataToServer(pageData)
            if(syncResult is Result.Success) {
                updateNotification("Successfully synced page details")
            }

            return syncResult
        }.getOrElse {
            Log.e(TAG, "doWork: ", it)
            updateNotification("Something went wrong while syncing page details")
            return Result.failure(errorData("Problem while updating page details"))
        }
    }

    private suspend fun getSelectedPageData(): PageData? {
        progress("Getting selected page data...")
        return when(val result = pagesRepo.getSelectedPage()) {
            is Success -> {
                // return page details
                result.data
            }

            is Failure -> {
                null
            }
        }
    }

    private suspend fun updateSelectedPageData(pageData: PageData): PageData? {
        progress("Getting updated page data...")
        return when(val result = pagesRepo.updatePage(pageData)) {
            is Success -> {
                // Cache updated page details
                pagesRepo.cachePage(result.data)

                // return page details
                result.data
            }

            is Failure -> {
                null
            }
        }
    }

    private suspend fun sendDataToServer(pageData: PageData): Result {
        progress("Syncing page data...")
        return when(pagesRepo.sendPageData(pageData)) {
            is Success -> {
               return Result.success()
            }

            is Failure -> {
                Result.failure(errorData("Problem while syncing page data"))
            }
        }
    }

    private fun createNotification(
        title: String = DEFAULT_NOTIFICATION_TITLE,
        body: String = DEFAULT_NOTIFICATION_BODY,
        id: String = DEFAULT_NOTIFICATION_ID,
        channelName: String = DEFAULT_NOTIFICATION_CHANNEL_NAME
    ): NotificationCompat.Builder {

        createChannel(id, channelName)

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_round_refresh)
            .setOngoing(true)


        this.notificationBuilder = notification

        return notification
    }

    private fun createChannel(
        id: String,
        channelName: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager?.createNotificationChannel(
                NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            )
        }
    }

    private fun updateNotification(msg: String) {
        notificationBuilder.setContentText(msg)
        notificationManager?.notify(1, notificationBuilder.build())
    }

    private fun errorData(msg: String) = workDataOf(Pair("error", msg))

    private suspend fun progress(msg: String) {
        setProgress(workDataOf(Pair(UPDATE_PAGE_DETAILS_PROGRESS, msg)))
    }


}