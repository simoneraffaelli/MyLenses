package sr.app.mylenses

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import sr.app.mylenses.utils.lang.StringsManager
import sr.app.mylenses.utils.log.tree.loggly.LogglyTree
import sr.app.mylenses.utils.log.tree.loggly.formatters.json.LogglyJsonFormatter
import sr.app.mylenses.utils.logglyToken
import sr.app.mylenses.utils.notifications.push.PushNotificationsHelper
import timber.log.Timber

class MyLensesApp : Application() {
    companion object {

        private lateinit var _instance: MyLensesApp

        val instance: MyLensesApp
            get() {
                return _instance
            }
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
        //Init StringsManager
        StringsManager.init()
        //Init Push
        PushNotificationsHelper.subscribeToAllMessages()
        //Init log
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(LogglyTree(logglyToken, LogglyJsonFormatter()))
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        }
    }
}