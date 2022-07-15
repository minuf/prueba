package com.example.remote.retrofit.factories

import com.example.remote.retrofit.data.VALUES
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitFactory {
    private val baseUrl = "https://gateway.marvel.com:443/v1/public/"

    fun makeRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(30, TimeUnit.SECONDS)

        client.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", VALUES.apiKey)
                .addQueryParameter("ts", VALUES.timeStamp)
                .addQueryParameter("hash", VALUES.hash)
                .build()

            chain.proceed(original.newBuilder().url(url).build())
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }
}

/*
interface MarvelApi {
    @GET("characters")
    fun allCharacters(@Query("offset") offset: Int? = 0): Observable<Response>

    companion object {
        fun getService(): MarvelApi {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", API_KEY)
                    .addQueryParameter("ts", ts)
                    .addQueryParameter("hash", "$ts$PRIVATE_KEY$API_KEY".md5())
                    .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

            return retrofit.create<MarvelApi>(MarvelApi::class.java)
        }
    }
}*/
