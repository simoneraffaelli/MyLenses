package sr.app.mylenses.utils.data.api.models

import com.google.errorprone.annotations.Keep
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.database.models.ResourceApiModel
import sr.app.mylenses.utils.data.enums.ResourceType
import sr.app.mylenses.utils.data.mapper.Mapper

@Keep
data class ResourceApiResponse(
    val fileName: String,
    val url: String,
    val type: String,
    val lastUpdated: DateTime
) : Mapper<ResourceApiModel> {
    override fun map(): ResourceApiModel {
        return ResourceApiModel(
            fileName = fileName,
            url = url,
            type = ResourceType.decode(type),
            lastUpdated = lastUpdated
        )
    }

}
