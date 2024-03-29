package sr.app.mylenses.utils.log

import com.google.firebase.crashlytics.FirebaseCrashlytics
import sr.app.mylenses.BuildConfig
import timber.log.Timber

internal class Log {
    companion object {
        private const val logPrefix = "SRLog-"

        internal fun logDebug(message: String?, className: String) {
            Timber.tag("$logPrefix$className").d(message)
        }

        internal fun logWarning(message: String?, className: String) {
            Timber.tag("$logPrefix$className").w(message)
        }

        internal fun logThrowable(throwable: Throwable, className: String, message: String? = null) {
            if (!BuildConfig.DEBUG) {
                FirebaseCrashlytics.getInstance().recordException(throwable)
            }
            Timber.tag("$logPrefix$className").e(throwable, message)
            throwable.printStackTrace()
        }
    }
}