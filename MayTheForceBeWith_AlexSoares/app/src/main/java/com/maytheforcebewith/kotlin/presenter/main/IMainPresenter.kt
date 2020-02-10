package com.maytheforcebewith.kotlin.presenter.main

interface IMainPresenter {
    fun getPeople(page: Int, isNextPage: Boolean)
}