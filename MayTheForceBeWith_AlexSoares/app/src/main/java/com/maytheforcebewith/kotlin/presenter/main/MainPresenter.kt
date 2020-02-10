package com.maytheforcebewith.kotlin.presenter.main

import android.content.Context
import com.maytheforcebewith.kotlin.R
import com.maytheforcebewith.kotlin.api.IConfigurationApi
import com.maytheforcebewith.kotlin.api.IRequestApi
import com.maytheforcebewith.kotlin.api.RequestApi
import com.maytheforcebewith.kotlin.helper.Util
import com.maytheforcebewith.kotlin.model.ResultsPeopleModel
import com.maytheforcebewith.kotlin.view.main.IMainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.NoSuchAlgorithmException

class MainPresenter() : IMainPresenter {
    private lateinit var mainView : IMainView
    private val requestApi: IRequestApi
    private lateinit var context : Context

    constructor(context: Context, mainView: IMainView) : this() {
        this.context = context
        this.mainView = mainView
    }


    override fun getPeople(page: Int, isNextPage: Boolean) {
        if (Util.isConnectedFast(context)) {
            try {
                requestApi.getPeopleRequest(page)
                    .enqueue(object : Callback<ResultsPeopleModel?> {
                        override fun onFailure(call: Call<ResultsPeopleModel?>, t: Throwable) {
                            mainView.errorRequest(context.getString(R.string.label_message_erro_generic))
                        }

                        override fun onResponse(call: Call<ResultsPeopleModel?>, response: Response<ResultsPeopleModel?>) {
                            if (response.body() != null) {
                                if (response.body()?.listPeople != null){
                                    if (isNextPage) {
                                        mainView.resultListPeopleNextPage(response.body()!!.listPeople!!)
                                    } else {
                                        mainView.resultListPeople(response.body()!!.listPeople!!)
                                    }
                                }else{
                                    mainView.resultListPeopleNextPage(null)
                                }
                            } else {
                                mainView.resultListPeopleNextPage(null)
                            }
                        }

                    })
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        } else {
            mainView.errorRequest(context.getString(R.string.label_message_erro_internet))
        }
    }

    init {
        requestApi = RequestApi(IConfigurationApi.retrofit.create(IConfigurationApi::class.java))
    }
}