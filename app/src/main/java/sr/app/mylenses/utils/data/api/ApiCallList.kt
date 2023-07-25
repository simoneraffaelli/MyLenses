package sr.app.mylenses.utils.data.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
import sr.app.mylenses.utils.data.api.models.ResourceApiResponse


interface ApiCallList {

    @GET("resources-list.json")
    suspend fun checkResourcesUpgrade(): Response<List<ResourceApiResponse>>

    @GET
    suspend fun downloadResource(@Url url: String): Response<JsonObject>
}