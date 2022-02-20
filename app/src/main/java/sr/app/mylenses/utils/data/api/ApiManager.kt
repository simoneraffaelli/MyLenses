package sr.app.mylenses.utils.data.api

import retrofit2.Response

class ApiManager {
    companion object {

        /* Api Call List */
        val testApiCall: (suspend () -> Response<Any>) =
            {
                ApiClient.apiManager!!.test()
            }
    }
}