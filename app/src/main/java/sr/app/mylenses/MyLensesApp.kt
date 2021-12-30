package sr.app.mylenses

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import sr.app.mylenses.utils.lang.StringsManager
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
        StringsManager::init
        //Init log
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            //TODO: create custom timber tree for prod env
        }
    }
}