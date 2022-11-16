package sr.app.mylenses.utils.sync

import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sr.app.mylenses.utils.log.w
import sr.app.mylenses.utils.login.GoogleSSOManager
import java.util.*

class GDriveSyncManager {
    companion object {
        private val driveService
            get() = Drive.Builder(
                NetHttpTransport(),
                GsonFactory(),
                GoogleSSOManager.credential
            )
                .build()

        suspend fun upload() {
            SynchableData.values().forEach {
                runCatching {
                    createFile(it.path(), it.fileName, it.mediaType)
                }.onFailure {
                    w(it)
                }
            }
        }

        suspend fun download() {
            getFiles()?.let { files ->
                files.forEach {
                    //save file in correct folder
                }
            }
        }

        private suspend fun createFile(
            filePath: String,
            fileName: String,
            type: okhttp3.MediaType
        ) {
            withContext(Dispatchers.IO) {
                val fileMetadata = File()
                fileMetadata.name = fileName
                fileMetadata.parents = Collections.singletonList(driveAppFolderScope)
                val file = java.io.File("$filePath$fileName")
                val mediaContent = FileContent(type.type, file)
                runCatching {
                    driveService.files().create(fileMetadata, mediaContent)
                        .setFields("id")
                        .execute()
                }.onFailure {
                    w(it)
                }
            }
        }

        private suspend fun getFiles(): List<File>? {
            var files: List<File>? = null
            withContext(Dispatchers.IO) {
                runCatching {
                    files = driveService.files().list()
                        .setSpaces(driveAppFolderScope)
                        .setFields("nextPageToken, files(id, name)")
                        .setPageSize(10)
                        .execute().files
                }
            }

            return files
        }
    }
}
