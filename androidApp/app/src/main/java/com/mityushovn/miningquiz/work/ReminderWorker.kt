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

/**
 * @author Nikita Mityushov
 * The WorkManager worker creates a notification with a pending intent that launch MainActivity
 * after user's click trigger.
 * @see MainActivity
 */
internal class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val CHANNEL_ID = "REMINDER_TO_TAKE_QUIZ_CHANNEL_1"
        const val PENDING_INTENT_REQUEST_CODE = 1001
        const val REMINDER_1_NOTIFICATION_ID = 12453
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

    /*
     * Creates a notification channel and a notification
     */
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
            .setSmallIcon(R.mipmap.logo_round)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setColorized(true)
            .setChronometerCountDown(true)
            .build()

        manager.notify(TAG, REMINDER_1_NOTIFICATION_ID, notification)
    }

    /*
     *  Creates and registers a channel
     */
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

    /*
     *  Creates a Pending Intent that triggers the MainActivity launch.
     */
    private fun createPendingIntent(): PendingIntent {
        val intentActivity = Intent(applicationContext, MainActivity::class.java)

        return PendingIntent.getActivity(
            applicationContext,
            PENDING_INTENT_REQUEST_CODE,
            intentActivity,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
