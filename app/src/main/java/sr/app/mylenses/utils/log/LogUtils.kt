package sr.app.mylenses.utils.log

private val defaultTag: String
    get() {
        var tag = "NoDefaultTag"
        runCatching {
            tag = Throwable().stackTrace[2].className.split('.').last()
        }

        return tag
    }

fun d(logMessage: String, logTag: String = defaultTag) {
    Log.logDebug(logMessage, logTag)
}

fun w(logMessage: String, logTag: String = defaultTag) {
    Log.logWarning(logMessage, logTag)
}

fun w(throwable: Throwable, logTag: String = defaultTag) {
    Log.logWarning(throwable.localizedMessage, logTag)
}

fun e(logThrowable: Throwable, logTag: String = defaultTag) {
    Log.logThrowable(logThrowable, logTag)
}

fun getLogTagByPriority(priority: Int): String {
    return when (priority) {
        2 -> "VERBOSE"
        3 -> "DEBUG"
        4 -> "INFO"
        5 -> "WARN"
        6 -> "ERROR"
        7 -> "ASSERT"
        else -> "WTF"
    }
}