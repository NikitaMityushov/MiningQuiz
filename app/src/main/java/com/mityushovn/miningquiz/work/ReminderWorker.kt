package com.mityushovn.miningquiz.work

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.main.presentation.activity.MainActivity

private const val TAG = "ReminderWorker"

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val CHANNEL_ID = "REMINDER_CHANNEL_1"
        const val PENDING_INTENT_REQUEST_CODE = 1001
    }

    override suspend fun doWork(): Result {
        // Do the work here
        return try {
            createNotificationWithPendingIntent()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotificationWithPendingIntent() {

        // 1) create and register Notification channel
        val manager = NotificationManagerCompat.from(applicationContext)
        createChannel(manager)

        // 2) create Notification
        val pendingIntent = createPendingIntent()
        val contentTitle = applicationContext.getString(R.string.reminder_1_content_title)
        val contentText = applicationContext.getString(R.string.reminder_1_content_text)


        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_baseline_quiz_24)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        manager.notify(TAG, PENDING_INTENT_REQUEST_CODE, notification)
    }

    private fun createChannel(manager: NotificationManagerCompat) {
        val channelName = applicationContext.getString(R.string.reminder_1_channel_name)
        val channelDescription =
            applicationContext.getString(R.string.reminder_1_channel_description)

        val channel = NotificationChannelCompat.Builder(
            CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_DEFAULT
        )
            .setName(channelName)
            .setDescription(channelDescription)
            .build()

        manager.createNotificationChannel(channel)
    }

    private fun createPendingIntent(): PendingIntent {
        val intentActivity = Intent(applicationContext, MainActivity::class.java)

        return PendingIntent.getActivity(
            applicationContext,
            PENDING_INTENT_REQUEST_CODE,
            intentActivity,
            0
        )
    }
}