package sr.app.mylenses.utils.log.tree.loggly.formatters.json.model

import com.google.gson.annotations.SerializedName

data class LogJsonModel(
    @SerializedName("level") val level: String,
    @SerializedName("tag") val tag: String,
    @SerializedName("message") val message: String,
    @SerializedName("stacktrace") val stacktrace: String?,
    @SerializedName("additional-detail") val additionalDetail: LogJsonAdditionalDetailModel
)