package sr.app.mylenses.utils.worker

import android.content.Context
import android.net.wifi.WifiManager
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.api.ApiManager
import sr.app.mylenses.utils.data.repository.RepositoryManager
import sr.app.mylenses.utils.log.w
import sr.app.mylenses.utils.notifications.local.NotificationBuilder
import sr.app.mylenses.utils.notifications.local.downloadWorkerNotificationId
import sr.app.mylenses.utils.notifications.local.workers.ProgressModel

class CheckUpdateWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {
    private val tag by lazy { this::class.java.simpleName }

    private lateinit var wifiLock: WifiManager.WifiLock

    override suspend fun doWork(): Result {
        val progress = "asd"
        setForeground(createForegroundInfo(progress))
        acquireWifiLock()
        runCatching {
            download()
            finishDownloadProcedure()
        }.onFailure {
            w(it)
            releaseWifiLock()
            return Result.failure()
        }
        releaseWifiLock()
        return Result.success()
    }

    private fun finishDownloadProcedure() {
        SyncManager.lastUpdateCheck = DateTime.now()
    }

    private suspend fun download() {
        val resources = ApiManager.checkResourcesUpdate.invoke()
        resources.body()?.forEach {
            RepositoryManager.resourcesRepository.updateFromApi(it.map())
        }
    }

    private fun createForegroundInfo(
        notificationText: String,
        progressModel: ProgressModel? = null
    ): ForegroundInfo {
        val notification = NotificationBuilder.buildWorkerServiceNotification(
            applicationContext,
            notificationText,
            WorkManager.getInstance(applicationContext).createCancelPendingIntent(id),
            progressModel
        )

        return ForegroundInfo(downloadWorkerNotificationId, notification.build())
    }

    private fun acquireWifiLock() {
        if (!this::wifiLock.isInitialized) {
            val wifiManager: WifiManager =
                applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, tag)
        }

        wifiLock.setReferenceCounted(false)
        if (!wifiLock.isHeld) {
            wifiLock.acquire()
        }
    }

    private fun releaseWifiLock() {
        if (this::wifiLock.isInitialized && wifiLock.isHeld) {
            wifiLock.release()
        } else {
            w("Unable to release wifi lock.", tag)
        }
    }

    companion object {
        //Input Tags
        const val syncMethod = "syncMethod"

        //Output tags
        const val progressDescTag = "progressDesc"
        const val progressPercTag = "progressPerc"
        const val progressCurrentToTotTag = "progressCurrToTot"
    }
}