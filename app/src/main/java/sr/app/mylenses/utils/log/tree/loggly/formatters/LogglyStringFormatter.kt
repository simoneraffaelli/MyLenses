package sr.app.mylenses.utils.log.tree.loggly.formatters

import sr.app.mylenses.utils.log.getLogTagByPriority

class LogglyStringFormatter : IFormatter {
    override fun format(priority: Int, tag: String?, message: String?, t: Throwable?): String {
        return StringBuilder()
            .append("[${getLogTagByPriority(priority)}||")
            .append("${tag ?: ""}]")
            .appendLine(message?.let { " $it" })
            .append(t?.let { " ${android.util.Log.getStackTraceString(it)}" })
            .toString()
    }
}