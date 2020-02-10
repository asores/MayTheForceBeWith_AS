package com.maytheforcebewith.kotlin.view.details

import android.app.ProgressDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.maytheforcebewith.kotlin.R
import com.maytheforcebewith.kotlin.presenter.details.DetailsPresenter
import com.maytheforcebewith.kotlin.presenter.details.IDetailsPresenter
import kotlinx.android.synthetic.main.activity_details.*

class DetailsPeopleActivity : AppCompatActivity(), IDetailsView {
    private var presenter: IDetailsPresenter? = null
    private var progressDialog: ProgressDialog? = null
    private var itemFavorite: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
    }

    private fun init() {
        showLoading()
        setSupportActionBar(toolbar as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        presenter = DetailsPresenter(this, this)
        presenter!!.getPeople(intent)
    }

    private fun saveFavoritePeople() {
        showLoading()
        presenter!!.saveFavorite()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details, menu)
        itemFavorite = menu.findItem(R.id.action_favorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> onBackPressed()
            R.id.action_favorite -> saveFavoritePeople()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showValuesPeople(
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
    ) {
        txt_name_people!!.text = name
        txt_height!!.text = height
        txt_mass!!.text = mass
        txt_hair_color!!.text = hairColor
        txt_skin_color!!.text = skinColor
        txt_eye_color!!.text = eyeColor
        txt_birth_year!!.text = birthYear
        txt_gender!!.text = gender
        txt_homeworld!!.setText(homeworld, TextView.BufferType.SPANNABLE)
        txt_homeworld!!.movementMethod = LinkMovementMethod.getInstance()
        txt_date_created!!.text = dateCreated
        txt_date_edited!!.text = dateEdited
        var stringBuilder = StringBuilder()
        for (film in listFilms!!) {
            stringBuilder.append(film)
            stringBuilder.append("\n")
        }
        txt_films!!.text = stringBuilder.toString()
        stringBuilder = StringBuilder()
        for (specie in listSpecies!!) {
            stringBuilder.append(specie)
            stringBuilder.append("\n")
        }
        txt_species!!.text = stringBuilder.toString()
        stringBuilder = StringBuilder()
        for (vehicles in listVehicles!!) {
            stringBuilder.append(vehicles)
            stringBuilder.append("\n")
        }
        txt_vehicles!!.text = stringBuilder.toString()
        stringBuilder = StringBuilder()
        for (starships in listStarships!!) {
            stringBuilder.append(starships)
            stringBuilder.append("\n")
        }
        txt_starships!!.text = stringBuilder.toString()
        hideLoading()
    }


    override fun sucessSaveFavorite(message: String?) {
        hideLoading()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        itemFavorite!!.icon = ContextCompat.getDrawable(this, android.R.drawable.star_big_on)
    }

    override fun erro(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        hideLoading()
    }

    private fun showLoading() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "", getString(R.string.label_loading))
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        } else if (!progressDialog!!.isShowing) {
            progressDialog!!.show()
        }
    }

    private fun hideLoading() {
        if (progressDialog != null) {
            progressDialog!!.cancel()
        }
    }
}