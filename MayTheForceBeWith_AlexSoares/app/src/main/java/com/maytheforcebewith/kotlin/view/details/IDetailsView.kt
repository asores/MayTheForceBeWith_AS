package com.maytheforcebewith.kotlin.view.details

import android.text.SpannableString

interface IDetailsView {
    fun showValuesPeople(
        name: String,
        height: String,
        mass: String?,
        hairColor: String?,
        skinColor: String?,
        eyeColor: String?,
        birthYear: String?,
        gender: String?,
        homeworld: SpannableString?,
        dateCreated: String?,
        dateEdited: String?,
        listFilms: List<String?>?,
        listSpecies: List<String?>?,
        listVehicles: List<String?>?,
        listStarships: List<String?>?
    )

    fun sucessSaveFavorite(message: String?)
    fun erro(error: String?)
}