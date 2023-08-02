package sr.app.mylenses.utils.notifications.local

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.enums.Type
import sr.app.mylenses.utils.data.model.LensPair
import java.util.Calendar

class NotificationScheduler {
    companion object {

        private fun setNotification(
            context: Context,
            date: DateTime,
            notificationId: Int,
            receiverClass: Class<*> = NotificationsBroadcastReceiver::class.java
        ) {
            val setCalendar = Calendar.getInstance().apply {
                this[Calendar.DAY_OF_YEAR] = date.dayOfYear
                this[Calendar.HOUR_OF_DAY] = date.hourOfDay
                this[Calendar.MINUTE] = date.minuteOfHour
                this[Calendar.SECOND] = date.secondOfMinute
            }
            // cancel already scheduled reminders
            cancelNotification(context, notificationId, receiverClass)
            if (setCalendar.before(Calendar.getInstance())) {
                setCalendar.add(Calendar.DATE, 1)
            }
            // Enable a receiver
            context.packageManager.setComponentEnabledSetting(
                ComponentName(context, receiverClass),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            val intent = Intent(context, receiverClass).apply {
                putExtra(notificationIdExtra, notificationId)
            }
            (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).setRepeating(
                AlarmManager.RTC,
                setCalendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(
                    context,
                    notificationId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        }

        fun cancelNotification(
            context: Context,
            notificationId: Int,
            receiverClass: Class<*> = NotificationsBroadcastReceiver::class.java
        ) {
            val intent = Intent(context, receiverClass)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                intent,
                0 or PendingIntent.FLAG_IMMUTABLE
            )
            (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(pendingIntent)
            pendingIntent.cancel()

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(
                null,
                notificationId
            )
        }

        public fun clearNotifications(
            context: Context,
            receiverClass: Class<*> = NotificationsBroadcastReceiver::class.java
        ) {
            Type.values().forEach {
                cancelNotification(context, getNotificationId(it), receiverClass)
            }
        }

        fun setNotifications(context: Context, pair: LensPair) {
            clearNotifications(context)
            if (pair.equalExpiration) {
                setNotification(
                    context,
                    date = pair.leftLens.expirationDate,
                    notificationId = getNotificationId(Type.Undefined)
                )
            } else {
                setNotification(
                    context,
                    date = pair.leftLens.expirationDate,
                    notificationId = getNotificationId(Type.Left)
                )
                setNotification(
                    context,
                    date = pair.rightLens.expirationDate,
                    notificationId = getNotificationId(Type.Right)
                )
            }
        }
    }
}