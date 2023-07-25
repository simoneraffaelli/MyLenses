package sr.app.mylenses.utils.log.tree.loggly

import androidx.annotation.Keep
import com.github.tony19.loggly.ILogglyClient
import com.github.tony19.loggly.LogglyClient
import sr.app.mylenses.utils.log.tree.loggly.formatters.IFormatter
import sr.app.mylenses.utils.log.tree.loggly.formatters.LogglyStringFormatter
import timber.log.Timber

@Keep
class LogglyTree(token: String, private var formatter: IFormatter = LogglyStringFormatter()) :
    Timber.Tree() {

    private val logglyClient: ILogglyClient = LogglyClient(token)
    private val handler: ILogglyClient.Callback = LogglyCallback()

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        logglyClient.log(formatter.format(priority, tag, message, t), handler)
    }
}
