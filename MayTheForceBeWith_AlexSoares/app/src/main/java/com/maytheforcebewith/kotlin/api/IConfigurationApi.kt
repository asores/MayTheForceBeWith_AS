package com.maytheforcebewith.kotlin.api

import com.google.gson.GsonBuilder
import com.maytheforcebewith.kotlin.helper.AppConstants
import com.maytheforcebewith.kotlin.model.PeopleModel
import com.maytheforcebewith.kotlin.model.ResultsPeopleModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.lang.reflect.Modifier

interface IConfigurationApi {
    @GET(AppConstants.PREFIX_URL)
    fun getPeople(@Query("page") page: Int): Call<ResultsPeopleModel?>

    @POST(AppConstants.PREFIX_URL_FAVORITE)
    fun saveFavorite(@Body people: PeopleModel?): Call<Any?>

    companion object {
       private val builder = GsonBuilder().excludeFieldsWithModifiers(
            Modifier.FINAL,
            Modifier.TRANSIENT,
            Modifier.STATIC
        )
        private val restResult = builder.create()
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(AppConstants.URL)
            .addConverterFactory(GsonConverterFactory.create(restResult))
            .build()
        val retrofitFavorite: Retrofit = Retrofit.Builder().baseUrl(AppConstants.URL_FAVORITE)
            .addConverterFactory(GsonConverterFactory.create(restResult))
            .build()
    }
}