package sr.app.mylenses.utils.notifications.push

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import sr.app.mylenses.utils.log.d

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
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