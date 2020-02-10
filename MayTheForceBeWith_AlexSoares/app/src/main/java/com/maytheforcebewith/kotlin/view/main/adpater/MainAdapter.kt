package com.maytheforcebewith.kotlin.view.main.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.maytheforcebewith.kotlin.R
import com.maytheforcebewith.kotlin.model.PeopleModel
import java.util.*

class MainAdapter() :
    RecyclerView.Adapter<MainHolder?>(), Filterable {
    var items = mutableListOf<PeopleModel>()
    var itemsFiltered = mutableListOf<PeopleModel>()

    constructor(itemsPeoples: List<PeopleModel>) : this() {
        items.addAll(itemsPeoples)
        itemsFiltered.addAll(itemsPeoples)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MainHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.main_adapter, viewGroup, false)
        return MainHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull mainHolder: MainHolder, position: Int) {
        val people: PeopleModel = itemsFiltered!![position]
        mainHolder.bind(people)
    }


    fun updateList(itemsUpdate: List<PeopleModel>) {
        items.addAll(itemsUpdate)
        itemsFiltered.addAll(itemsUpdate)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                itemsFiltered = if (charString.isEmpty()) {
                    items
                } else {
                    val filteredList: MutableList<PeopleModel> = ArrayList()
                    for (peopleModel in items!!) {
                        if (peopleModel.name!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(peopleModel)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsFiltered
                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence,
                results: FilterResults
            ) {
                itemsFiltered = results.values as ArrayList<PeopleModel>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (itemsFiltered != null) itemsFiltered!!.size else 0
    }
}