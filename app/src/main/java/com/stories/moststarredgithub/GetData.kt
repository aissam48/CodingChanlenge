package com.stories.moststarredgithub

import retrofit2.Call
import retrofit2.http.GET

interface GetData {

    @GET("")
    fun getDataFromGithub(): Call<ModulData>
}