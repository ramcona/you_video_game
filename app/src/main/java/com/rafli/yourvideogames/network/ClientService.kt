package com.rafli.yourvideogames.network

import com.rafli.yourvideogames.helper.Config.BASE_API
import com.rafli.yourvideogames.helper.Validasi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ClientService {

    fun create(): ApiServiceServer {
        var cTO:Long = 30
        var wTO:Long = 30
        var rTO:Long = 30

        val interceptor = HttpLoggingInterceptor(Logger())
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient: OkHttpClient = if (Validasi().isRelease()) {
            OkHttpClient.Builder()
                .connectTimeout(cTO, TimeUnit.SECONDS)
                .writeTimeout(wTO, TimeUnit.SECONDS)
                .readTimeout(rTO, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(interceptor) //iterceptor hanya untuk debug link tidak untuk release
                .connectTimeout(cTO, TimeUnit.SECONDS)
                .writeTimeout(wTO, TimeUnit.SECONDS)
                .readTimeout(rTO, TimeUnit.SECONDS)
                .build()
        }


        val retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(BASE_API)
            .client(okHttpClient)
            .build()


        return retrofit.create(ApiServiceServer::class.java)
    }


    /*UNTUK UPLOAD FILE*/
    private var retrofit: Retrofit? = null

}