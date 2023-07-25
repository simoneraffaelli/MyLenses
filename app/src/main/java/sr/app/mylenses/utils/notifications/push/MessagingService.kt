package sr.app.mylenses.utils.notifications.push

import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import sr.app.mylenses.utils.log.d
import sr.app.mylenses.utils.notifications.local.NotificationScheduler

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        kotlin.runCatching {
            Toast.makeText(applicationContext, "AAAAASSSDDD", Toast.LENGTH_LONG).show()
        }
        d("Message data payload: ${remoteMessage.data}")
        if (remoteMessage.data.isNotEmpty()) {
            d("Message data payload: ${remoteMessage.data}")
            //sheduleResDownload
        }
    }

    override fun onNewToken(token: String) {
        d("Refreshed token: $token")
        //Do Nothing
    }
}