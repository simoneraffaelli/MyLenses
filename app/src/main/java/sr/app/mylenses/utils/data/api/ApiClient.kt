package sr.app.mylenses.utils.data.api

import android.widget.Toast
import sr.app.mylenses.MyLensesApp
import sr.app.mylenses.utils.lang.StringsManager
import sr.app.mylenses.utils.log.e

object ApiClient {
    var apiManager: ApiCallList? = null
        get() {
            if (field == null) {
                field = createApiManagerClient()
            }
            return field
        }
        private set

    private fun createApiManagerClient(): ApiCallList? {
        var amClient: ApiCallList? = null
        runCatching {
            amClient = ServiceGenerator.createService(
                ApiCallList::class.java
            )
        }.onFailure {
            Toast.makeText(
                MyLensesApp.instance,
                StringsManager.get("AASSDD"),
                Toast.LENGTH_LONG
            )
                .show()
            e(it)
        }

        return amClient
    }
}