package sr.app.mylenses.utils.data

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

private val documentBasePath = "resources/"

fun documentFullPath(context: Context, assetPath: String) =
    "${context.filesDir}${File.separator}$documentBasePath$assetPath"

fun load(context: Context, assetPath: String, fileName: String): InputStream {
    val filePath = "${documentFullPath(context, assetPath)}${File.separator}$fileName"
    return File(filePath).takeIf { it.exists() }?.let { FileInputStream(it) }
        ?: context.assets.open("$assetPath$fileName")
}

fun saveByteStreamToInternalStorage(
    context: Context,
    byteStream: InputStream,
    fileName: String,
    fullPath: String? = null
) {
    byteStream.use { input ->
        File(
            fullPath ?: "${context.filesDir}${File.separator}",
            fileName
        ).apply { parentFile?.mkdirs() }.outputStream().use { output ->
            input.copyTo(output)
        }
    }
}