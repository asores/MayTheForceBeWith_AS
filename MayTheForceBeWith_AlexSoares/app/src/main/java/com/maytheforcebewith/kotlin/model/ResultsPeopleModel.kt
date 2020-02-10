package com.maytheforcebewith.kotlin.model

import com.google.gson.annotations.SerializedName

class ResultsPeopleModel {
    @SerializedName("results")
    lateinit var listPeople: List<PeopleModel>

}