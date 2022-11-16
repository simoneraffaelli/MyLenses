package sr.app.mylenses.utils.notifications.local

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationManagerCompat

class NotificationsHelper {
    companion object {
        fun notify(context: Context, notificationId: Int, notification: Notification) {
            with(NotificationManagerCompat.from(context)) {
                notify(notificationId, notification)
            }
        }
    }
}