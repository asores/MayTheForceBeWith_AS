package com.maytheforcebewith.kotlin.presenter.details

import android.content.Intent

interface IDetailsPresenter {
    fun getPeople(intent: Intent?)
    fun saveFavorite()
}