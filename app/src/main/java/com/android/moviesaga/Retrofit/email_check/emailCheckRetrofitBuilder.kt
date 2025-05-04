package com.example.moviesaga.Retrofit.email_check

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EmailCheckRetrofitBuilder {
    private val baseurl = "https://verify.maileroo.net/"

    val getApi: EmailCheckApiServices by lazy {
        Retrofit.Builder().baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create()) // convert json to actual object(here gson do this)
            .build()
            .create(EmailCheckApiServices::class.java)
    }
}