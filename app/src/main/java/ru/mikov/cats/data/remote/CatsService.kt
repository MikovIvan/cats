package ru.mikov.cats.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mikov.cats.data.models.Cat
import ru.mikov.cats.data.remote.interceptors.NetworkStatusInterceptor

interface CatsService {

    @GET("images/search?order=Desc")
    suspend fun getCats(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<List<Cat>>

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com/v1/"
        fun create(): CatsService {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(
                    Interceptor { chain ->
                        val request: Request = chain.request().newBuilder()
                            .addHeader("x-api-key", "96c6848b-6e93-41fd-b310-3c429cac4983")
                            .build()
                        chain.proceed(request)
                    }
                )
                .addInterceptor(NetworkStatusInterceptor())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(CatsService::class.java)
        }
    }
}
