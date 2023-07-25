package sr.app.mylenses.utils.log.tree.loggly

import com.github.tony19.loggly.ILogglyClient

class LogglyCallback : ILogglyClient.Callback {
    override fun success() {
        // ignore
    }

    override fun failure(error: String) {
        System.err.println("LogglyTree failed: $error")
    }
}