package com.maytheforcebewith.kotlin.api

import com.maytheforcebewith.kotlin.model.PeopleModel
import com.maytheforcebewith.kotlin.model.ResultsPeopleModel
import retrofit2.Call
import java.security.NoSuchAlgorithmException

interface IRequestApi {
    @Throws(NoSuchAlgorithmException::class)
    fun getPeopleRequest(page: Int): Call<ResultsPeopleModel?>

    @Throws(NoSuchAlgorithmException::class)
    fun savePeopleFavorite(peopleModel: PeopleModel?): Call<Any?>
}