package sr.app.mylenses.utils.notifications.local.workers

class ProgressModel(progress: Number, max: Number) {
    val progress: Float = progress.toFloat()
    val max: Float = max.toFloat()
}