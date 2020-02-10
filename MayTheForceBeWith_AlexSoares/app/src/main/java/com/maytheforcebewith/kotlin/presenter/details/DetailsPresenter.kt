package com.maytheforcebewith.kotlin.presenter.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import androidx.annotation.NonNull
import com.maytheforcebewith.kotlin.R
import com.maytheforcebewith.kotlin.api.IConfigurationApi
import com.maytheforcebewith.kotlin.api.IRequestApi
import com.maytheforcebewith.kotlin.api.RequestApi
import com.maytheforcebewith.kotlin.helper.AppConstants
import com.maytheforcebewith.kotlin.helper.Util
import com.maytheforcebewith.kotlin.model.PeopleModel
import com.maytheforcebewith.kotlin.view.details.IDetailsView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat

class DetailsPresenter(private val context: Context, detailsView: IDetailsView) :
    IDetailsPresenter {

    private var people: PeopleModel? = null
    private val detailsView: IDetailsView = detailsView
    private var requestApi: IRequestApi? = null


    fun prepareLink(url: String): SpannableString {
        val spannableStringLink =
            SpannableString(context.getString(R.string.label_link_one))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(@NonNull widget: View) {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
        spannableStringLink.setSpan(
            clickableSpan, 0,
            spannableStringLink.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableStringLink
    }

    override fun getPeople(intent: Intent?) = if (intent!!.extras != null) {
        people = intent.extras!!.getSerializable(AppConstants.BUNDLE_PEOPLE) as PeopleModel?
        if (people != null) {
            val simpleFormatter =
                SimpleDateFormat("dd/MM/yyyy")
            people!!.name.let {
                people!!.height.let { it1 ->
                    detailsView.showValuesPeople(
                        it, it1, people!!.mass, people!!.colorHair,
                        people!!.colorSkin, people!!.colorEye, people!!.yearBirth, people!!.gender,
                        people!!.homeworld?.let { prepareLink(it) }, simpleFormatter.format(people!!.created),
                        simpleFormatter.format(people!!.edited), people!!.listFilms, people!!.listSpecies,
                        people!!.listVehicles, people!!.listStarships
                    )
                }
            }
        } else {
            detailsView.erro("")
        }
    } else {
        detailsView.erro("")
    }

    override fun saveFavorite() {
        if (Util.isConnectedFast(context)) {
            requestApi =
                RequestApi(IConfigurationApi.retrofitFavorite.create(IConfigurationApi::class.java))
            try {
                requestApi!!.savePeopleFavorite(people)!!.enqueue(object : Callback<Any?> {
                    override fun onResponse(
                        call: Call<Any?>?,
                        response: Response<Any?>?
                    ) {
                        detailsView.sucessSaveFavorite(context.getString(R.string.label_message_sucess))
                    }

                    override fun onFailure(call: Call<Any?>?, t: Throwable?) {
                        detailsView.erro(context.getString(R.string.label_message_erro_generic))
                    }
                })
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        } else {
            detailsView.erro(context.getString(R.string.label_message_erro_internet))
        }
    }

}