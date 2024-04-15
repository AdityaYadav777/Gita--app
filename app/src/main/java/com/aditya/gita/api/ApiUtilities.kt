package com.aditya.gita.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {

    val headers= mapOf<String,String>(
        "Accept" to "application/json",
        "X-RapidAPI-Key" to "b2787068b8mshc50e0100b304c44p1b9225jsnc53d4cd7a394",
        "X-RapidAPI-Host" to "bhagavad-gita3.p.rapidapi.com"
    )

    val clinet=OkHttpClient.Builder().apply {
        addInterceptor{chain->

            val newRequest=chain.request().newBuilder().apply {
                headers.forEach{key,value-> addHeader(key,value)}
            }.build()
       chain.proceed(newRequest)
        }
    }.build()


    val api:interfaceApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://bhagavad-gita3.p.rapidapi.com")
            .client(clinet)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(interfaceApi::class.java)
    }

}