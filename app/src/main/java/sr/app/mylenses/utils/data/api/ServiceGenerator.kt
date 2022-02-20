package sr.app.mylenses.utils.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sr.app.mylenses.BuildConfig
import sr.app.mylenses.utils.data.defaultApiBaseUrl

class ServiceGenerator {
    companion object {
        private val httpClient get() = OkHttpClient.Builder()
        private val logInterceptor: HttpLoggingInterceptor by lazy {
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        }
        private val builder: Retrofit.Builder
            get() {
                return Retrofit.Builder().addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().serializeNulls().create()
                    )
                )
            }

        fun <S> createService(serviceClass: Class<S>, baseUrl: String = defaultApiBaseUrl): S {
            /* Add Logging Interceptor */
            val httpClient =
                httpClient.apply { if (BuildConfig.DEBUG) addInterceptor(logInterceptor) }
            /* Set Last Builder Params */
            val builder = builder.apply {
                this.baseUrl(baseUrl)
                this.client(httpClient.build())
            }
            /* Build Api Service Class */
            return builder.build().create(serviceClass)
        }
    }
}