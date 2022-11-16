package sr.app.mylenses.utils.sync

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import sr.app.mylenses.utils.data.databaseName
import sr.app.mylenses.utils.preferences.SPFileName
import sr.app.mylenses.utils.preferences.encryptedSPFileName

enum class SynchableData(
    val path: () -> String,
    val fileName: String,
    val mediaType: MediaType
) {
    Database({ "" }, databaseName, "".toMediaType()),
    SharedPrefrences({ "" }, SPFileName, "".toMediaType()),
    EncryptesSharedPreferences({ "" }, encryptedSPFileName, "".toMediaType())


}