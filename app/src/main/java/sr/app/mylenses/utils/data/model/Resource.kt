package sr.app.mylenses.utils.data.model

import android.content.Context
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.database.models.ResourceModel
import sr.app.mylenses.utils.data.documentFullPath
import sr.app.mylenses.utils.data.enums.ResourceType
import sr.app.mylenses.utils.data.mapper.Mapper

class Resource(
    val fileName: String,
    val url: String,
    val type: ResourceType,
    val lastUpdated: DateTime,
    val lastSynchronized: DateTime? =  null
) : Mapper<ResourceModel> {

    override fun map(): ResourceModel {
        return ResourceModel(fileName, url, type, lastUpdated, lastSynchronized)
    }

    fun filePath(context: Context): String {
        return documentFullPath(context, type.assetPath, fileName)
    }
}