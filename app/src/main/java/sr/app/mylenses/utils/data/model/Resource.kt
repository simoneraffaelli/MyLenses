package sr.app.mylenses.utils.data.model

import android.content.Context
import org.joda.time.DateTime
import sr.app.mylenses.utils.data.database.models.ResourceModel
import sr.app.mylenses.utils.data.documentFullPath
import sr.app.mylenses.utils.data.enums.ResourceType
import sr.app.mylenses.utils.data.mapper.Mapper

class Resource(
    val fileName: String,
    val type: ResourceType,
    val syncDate: DateTime?,
    var runSync: Boolean = false
) : Mapper<ResourceModel> {

    override fun map(): ResourceModel {
        return ResourceModel(fileName, type, syncDate)
    }

    fun filePath(context: Context): String {
        return documentFullPath(context, type.assetPath, fileName)
    }
}