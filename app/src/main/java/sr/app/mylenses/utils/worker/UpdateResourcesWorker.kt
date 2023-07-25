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
import sr.app.mylenses.utils.data.saveByteStreamToInternalStorage
import sr.app.mylenses.utils.lang.StringsManager
import sr.app.mylenses.utils.log.w
import sr.app.mylenses.utils.notifications.local.NotificationBuilder
import sr.app.mylenses.utils.notifications.local.downloadWorkerNotificationId
import sr.app.mylenses.utils.notifications.local.workers.ProgressModel

class UpdateResourcesWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {
    private val tag by lazy { this::class.java.simpleName }

    private lateinit var wifiLock: WifiManager.WifiLock

    override suspend fun doWork(): Result {
        setForeground(createForegroundInfo(StringsManager.get("checkingUpdates")))
        acquireWifiLock()
        runCatching {
            checkForUpdates()
            setForeground(createForegroundInfo(StringsManager.get("downloadStarting")))
            download()
        }.onFailure {
            w(it)
            releaseWifiLock()
            return Result.failure()
        }
        releaseWifiLock()
        return Result.success()
    }

    private suspend fun checkForUpdates() {
        val resources = ApiManager.checkResourcesUpdate.invoke()
        resources.body()?.forEach {
            RepositoryManager.resourcesRepository.updateFromApi(it.map())
        }

        SyncManager.lastUpdateCheck = DateTime.now()
    }

    private suspend fun download() {
        val res = RepositoryManager.resourcesRepository.resourcesToDownload
        val max = res.count()
        val now = DateTime.now()
        res.forEachIndexed { idx, resource ->
            runCatching {
                val resp = ApiManager.downloadResource.invoke(resource.url)
                saveByteStreamToInternalStorage(
                    context = applicationContext,
                    byteStream = resp.body().toString().toByteArray().inputStream(),
                    fileName = resource.fileName,
                    fullPath = resource.filePath(applicationContext)
                )

                setForeground(
                    createForegroundInfo(
                        StringsManager.get("downloadProgress", "$idx", "$max"),
                        ProgressModel(idx, max)
                    )
                )

                RepositoryManager.resourcesRepository.updateSyncDate(resource.fileName, now)
            }.onFailure {
                w(it)

                setForeground(
                    createForegroundInfo(
                        StringsManager.get("downloadProgress", "$idx", "$max"),
                        ProgressModel(idx, max)
                    )
                )
            }
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
}