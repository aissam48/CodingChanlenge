package com.stories.moststarredgithub

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class mRetrofit {

    fun fRetrofit():Retrofit{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()

        return retrofit
    }

}