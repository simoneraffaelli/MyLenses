package sr.app.mylenses.utils.notifications.local

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import sr.app.mylenses.MainActivity
import sr.app.mylenses.R
import sr.app.mylenses.utils.notifications.local.workers.ProgressModel

class NotificationBuilder {

    companion object {

        fun build(context: Context, notificationId: Int): Notification {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val snoozeIntent = Intent(context, NotificationsBroadcastReceiver::class.java).apply {
                action = actionSilence
                putExtra(notificationIdExtra, notificationId)
            }
            val snoozePendingIntent: PendingIntent =
                PendingIntent.getBroadcast(context, notificationId, snoozeIntent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            createNotificationChannel(context)
            return NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_logo_notification)
                .setContentTitle(context.resources.getString(R.string.notification_title))
                .setContentText(context.resources.getString(R.string.notification_body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(
                    /* icon = */ R.drawable.ic_silent,
                    /* title = */ context.resources.getString(R.string.notification_silence_label),
                    /* intent = */ snoozePendingIntent
                )
                .build()
        }

        private fun createNotificationChannel(context: Context) {
            val name = "Default"
            val descriptionText = "Default"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
                enableVibration(true)
                vibrationPattern =
                    longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        fun buildWorkerServiceNotification(
            ctx: Context,
            text: String,
            cancelIntent: PendingIntent,
            progressModel: ProgressModel? = null
        ): NotificationCompat.Builder {
            createNotificationChannel(ctx)
            // Create the notification itself
            return NotificationCompat.Builder(ctx, channelId)
                .setContentTitle("Download Service")
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_download)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_clear, ctx.resources.getString(R.string.notification_cancel_label), cancelIntent)
                .setProgress(
                    progressModel?.max?.toInt() ?: 0,
                    progressModel?.progress?.toInt() ?: 0,
                    progressModel == null
                )
        }
    }
}