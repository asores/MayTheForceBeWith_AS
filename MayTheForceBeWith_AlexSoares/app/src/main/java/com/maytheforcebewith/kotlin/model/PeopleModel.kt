package com.maytheforcebewith.kotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class PeopleModel : Serializable {
    @SerializedName("name")
    lateinit var name: String
    @SerializedName("height")
    lateinit var height: String
    @SerializedName("mass")
    lateinit var mass: String
    @SerializedName("hair_color")
    lateinit var colorHair: String
    @SerializedName("skin_color")
    lateinit var colorSkin: String
    @SerializedName("eye_color")
    lateinit var colorEye: String
    @SerializedName("birth_year")
    lateinit var yearBirth: String
    @SerializedName("gender")
    lateinit var gender: String
    @SerializedName("homeworld")
    lateinit var homeworld: String
    @SerializedName("films")
    lateinit var listFilms: List<String>
    @SerializedName("species")
    lateinit var listSpecies: List<String>
    @SerializedName("vehicles")
    lateinit var listVehicles: List<String>
    @SerializedName("starships")
    lateinit var listStarships: List<String>
    @SerializedName("created")
    lateinit var created: Date
    @SerializedName("edited")
    lateinit var edited: Date
    @SerializedName("url")
    lateinit var url: String

}