package sr.app.mylenses.utils.worker

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import org.joda.time.DateTime
import sr.app.mylenses.R
import sr.app.mylenses.utils.preferences.SharedPreferencesManager

class SyncManager {
    companion object {

        var lastUpdateCheck: DateTime?
            get() = SharedPreferencesManager.lastUpdateCheckSP
            set(value) {
                SharedPreferencesManager.lastUpdateCheckSP = value
            }

        fun createDownloadWorker(ctx: Context) {
            val tag = ctx.resources.getString(R.string.download_worker_service_tag)
            val oneTimeRequest: OneTimeWorkRequest =
                OneTimeWorkRequestBuilder<UpdateResourcesWorker>()
                    .addTag(tag)
                    .build()

            WorkManager.getInstance(ctx).enqueueUniqueWork(
                tag,
                ExistingWorkPolicy.REPLACE,
                oneTimeRequest
            )
        }
    }
}