package com.maytheforcebewith.kotlin.view.main


import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.maytheforcebewith.kotlin.R
import com.maytheforcebewith.kotlin.model.PeopleModel
import com.maytheforcebewith.kotlin.presenter.main.IMainPresenter
import com.maytheforcebewith.kotlin.presenter.main.MainPresenter
import com.maytheforcebewith.kotlin.view.main.adpater.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainView {

    private var presenter: IMainPresenter? = null
    private lateinit var searchView: SearchView
    private var adapter: MainAdapter? = null
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var layoutManager: LinearLayoutManager? = null
    private var page = 1
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        showLoading()
        presenter = MainPresenter(this, this)
        presenter?.getPeople(page, false)
        setSupportActionBar(toolbar as Toolbar?)
        srlUpdatePeoples.setColorSchemeColors(
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.colorPrimaryDark),
            resources.getColor(R.color.colorAccent)
        )
        srlUpdatePeoples.setOnRefreshListener {
            if (searchView!!.isIconified) {
                srlUpdatePeoples.isRefreshing = true
                showLoading()
                page = 1
                presenter?.getPeople(page, false)
            } else {
                srlUpdatePeoples.isRefreshing = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val actionSearch = menu.findItem(R.id.action_search)
        searchView = actionSearch.actionView as SearchView
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!searchView!!.isIconified) {
                    searchView!!.isIconified = true
                }
                actionSearch.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }
        })
        return true
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

    override fun resultListPeople(peopleList: List<PeopleModel>) {
        adapter = MainAdapter(peopleList)
        layoutManager = LinearLayoutManager(this)
        rvPeoples.layoutManager = layoutManager
        rvPeoples.adapter = adapter
        rvPeoples.animate().setDuration(1000)
        srlUpdatePeoples.setRefreshing(false)
        hideLoading()
        rvPeoples.addOnScrollListener(onScrollListener)
    }

    override fun resultListPeopleNextPage(peopleList: List<PeopleModel>?) {
        if (peopleList != null) {
            if (adapter == null) {
                resultListPeople(peopleList)
            } else {
                adapter!!.updateList(peopleList)
            }
        }
        srlUpdatePeoples.isRefreshing = false
        hideLoading()
    }

    override fun errorRequest(error: String?) {
        srlUpdatePeoples.isRefreshing = false
        hideLoading()
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }


    private var onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0 && searchView!!.isIconified) {
                visibleItemCount = layoutManager!!.childCount
                totalItemCount = layoutManager!!.itemCount
                pastVisiblesItems = layoutManager!!.findFirstVisibleItemPosition()
                if (visibleItemCount + pastVisiblesItems == totalItemCount) {
                    totalItemCount += totalItemCount
                    if (presenter != null) {
                        page += 1
                        presenter!!.getPeople(page, true)
                        showLoading()
                    } else {
                        init()
                    }
                }
            }
        }
    }
}
