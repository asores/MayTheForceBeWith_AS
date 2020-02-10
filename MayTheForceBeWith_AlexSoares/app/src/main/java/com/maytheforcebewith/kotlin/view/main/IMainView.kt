package com.maytheforcebewith.kotlin.view.main

import com.maytheforcebewith.kotlin.model.PeopleModel

interface IMainView {
    fun resultListPeople(peopleList: List<PeopleModel>)
    fun resultListPeopleNextPage(peopleList: List<PeopleModel>?)
    fun errorRequest(error: String?)
}