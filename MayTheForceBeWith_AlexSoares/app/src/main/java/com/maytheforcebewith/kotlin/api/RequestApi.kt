package com.maytheforcebewith.kotlin.api

import com.maytheforcebewith.kotlin.model.PeopleModel
import com.maytheforcebewith.kotlin.model.ResultsPeopleModel
import retrofit2.Call
import java.security.NoSuchAlgorithmException

class RequestApi(private val configurationApi: IConfigurationApi) : IRequestApi {
    @Throws(NoSuchAlgorithmException::class)
    override fun getPeopleRequest(page: Int): Call<ResultsPeopleModel?> {
        return configurationApi.getPeople(page)
    }

    @Throws(NoSuchAlgorithmException::class)
    override fun savePeopleFavorite(peopleModel: PeopleModel?): Call<Any?> {
        return configurationApi.saveFavorite(peopleModel)
    }

}