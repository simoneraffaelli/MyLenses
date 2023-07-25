package sr.app.mylenses.utils.notifications.push

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import sr.app.mylenses.utils.log.d
import sr.app.mylenses.utils.worker.SyncManager

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        d("Remote message data payload: ${remoteMessage.data}")
        if (remoteMessage.data.isNotEmpty()) {
            SyncManager.createDownloadWorker(applicationContext)
        }
    }

    override fun onNewToken(token: String) {
        //Do Nothing
    }
}