package com.stories.moststarredgithub

import com.stories.moststarredgithub.PackageModulData.ModulData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GetData {



    @GET
    fun getDataFromGithub(@Url url:String): Call<ModulData>
}