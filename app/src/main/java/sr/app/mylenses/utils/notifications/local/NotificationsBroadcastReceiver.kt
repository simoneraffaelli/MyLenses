package sr.app.mylenses.utils.notifications.local

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sr.app.mylenses.utils.data.repository.RepositoryManager

class NotificationsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context, intent: Intent) {
        intent.action?.let {
            when (it) {
                Intent.ACTION_BOOT_COMPLETED -> {
                    CoroutineScope(Dispatchers.Default).launch {
                        RepositoryManager.lensesRepository.activeLensPair?.let {
                            withContext(Dispatchers.Main) {
                                NotificationScheduler.setNotifications(ctx, it)
                            }
                        }
                    }
                }
                actionSilence -> {
                    intent.extras?.getInt(notificationIdExtra, 0)?.let { notifId ->
                        NotificationScheduler.cancelNotification(
                            ctx,
                            notifId,
                            javaClass
                        )
                        CoroutineScope(Dispatchers.Default).launch {
                            RepositoryManager.lensesRepository.deactivateActiveLenses()
                        }
                    }
                }
            }

            return
        }

        val notificationId = intent.getIntExtra(notificationIdExtra, -1)
        NotificationsHelper.notify(
            ctx,
            notificationId,
            NotificationBuilder.build(ctx, notificationId)
        )
    }
}