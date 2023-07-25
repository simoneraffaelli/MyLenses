package sr.app.mylenses.utils.log.tree.loggly.formatters.json

import android.os.Build
import android.util.Log
import com.google.gson.Gson
import sr.app.mylenses.BuildConfig
import sr.app.mylenses.utils.log.getLogTagByPriority
import sr.app.mylenses.utils.log.tree.loggly.formatters.IFormatter
import sr.app.mylenses.utils.log.tree.loggly.formatters.json.model.LogJsonAdditionalDetailModel
import sr.app.mylenses.utils.log.tree.loggly.formatters.json.model.LogJsonModel

class LogglyJsonFormatter : IFormatter {
    override fun format(priority: Int, tag: String?, message: String?, t: Throwable?): String {
        val model = LogJsonModel(
            level = getLogTagByPriority(priority = priority),
            tag = tag ?: "",
            message = message ?: "",
            stacktrace = t?.let { " ${Log.getStackTraceString(it)} " },
            additionalDetail = LogJsonAdditionalDetailModel(
                appVersion = BuildConfig.VERSION_NAME,
                androidVersion = Build.VERSION.RELEASE,
                device = "${Build.MANUFACTURER}_${Build.MODEL},"
            )

        )
        return Gson().toJson(model)
    }
}