package sr.app.mylenses.utils.log.tree.loggly.formatters.json.model

import com.google.gson.annotations.SerializedName

data class LogJsonAdditionalDetailModel(
    @SerializedName("app-version") val appVersion: String,
    @SerializedName("android-version") val androidVersion: String,
    @SerializedName("device") val device: String
)