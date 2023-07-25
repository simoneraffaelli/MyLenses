package sr.app.mylenses.utils.data.api

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
            e(it)
        }

        return amClient
    }
}