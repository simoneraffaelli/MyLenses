package sr.app.mylenses.utils.log.tree.loggly.formatters

interface IFormatter {
    fun format(priority: Int, tag: String?, message: String?, t: Throwable?): String?
}