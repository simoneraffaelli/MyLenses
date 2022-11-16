package sr.app.mylenses.utils.notifications.push

import com.google.firebase.messaging.FirebaseMessaging

class PushNotificationsHelper {
    companion object {
        fun subscribeToAllMessages() {
            FirebaseMessaging.getInstance().subscribeToTopic(topicAllMessages)
        }
    }
}