package sr.app.mylenses.utils.data.api

import com.google.gson.JsonObject
import retrofit2.Response
import sr.app.mylenses.utils.data.api.models.ResourceApiResponse

class ApiManager {
    companion object {

        /* Api Call List */
        val checkResourcesUpdate: (suspend () -> Response<List<ResourceApiResponse>>) =
            {
                ApiClient.apiManager!!.checkResourcesUpgrade()
            }

        val downloadResource: (suspend (url: String) -> Response<JsonObject>) =
            {
                ApiClient.apiManager!!.downloadResource(it)
            }
    }
}