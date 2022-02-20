package sr.app.mylenses.utils.data.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiCallList {

    @GET()
    fun test(): Response<Any>
}