package com.stories.moststarredgithub

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class mRetrofit {

    fun fRetrofit(page :String):Retrofit{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/search/repositories?q=created:>2017-10-22&sort=stars&order=desc&page=$page")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()

        return retrofit
    }

}