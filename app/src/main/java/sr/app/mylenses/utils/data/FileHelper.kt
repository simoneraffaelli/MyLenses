package sr.app.mylenses.utils.data

import android.content.Context
import com.google.gson.JsonObject
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

private val documentBasePath = "resources/"

fun documentFullPath(context: Context, assetPath: String, fileName: String) =
    "${context.filesDir}$documentBasePath$assetPath$fileName"

fun load(context: Context, assetPath: String, fileName: String): InputStream {
    val filePath = documentFullPath(context, assetPath, fileName)
    return File(filePath).takeIf { it.exists() }?.let { FileInputStream(it) }
        ?: context.assets.open("$assetPath$fileName")
}

fun write(
    json: JsonObject,
    filePath: String
) {
    val f = File(filePath)
    if (!f.exists()) {
        val folder = File(f.parent!!)

        if (!folder.exists()) {
            folder.mkdirs()
        }
    }

    f.writeText(json.toString())
}