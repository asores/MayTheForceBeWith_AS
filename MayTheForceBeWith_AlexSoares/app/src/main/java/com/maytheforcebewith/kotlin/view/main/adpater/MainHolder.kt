package com.maytheforcebewith.kotlin.view.main.adpater

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.maytheforcebewith.kotlin.helper.AppConstants
import com.maytheforcebewith.kotlin.model.PeopleModel
import com.maytheforcebewith.kotlin.view.details.DetailsPeopleActivity
import java.text.SimpleDateFormat
import kotlinx.android.synthetic.main.main_adapter.view.*

class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val context: Context = itemView.context
    private var peopleModel: PeopleModel? = null

    fun bind(people: PeopleModel) {
        peopleModel = people
        val simpleFormatter = SimpleDateFormat("dd/MM/yyyy")
        super.itemView.txt_name_people.text = people.name
        super.itemView.txt_height.text = people.height
        super.itemView.txt_gender.text = people.gender
        super.itemView.txt_date_create.text = simpleFormatter.format(people.created)
        super.itemView.txt_date_edited.text = simpleFormatter.format(people.edited)

        super.itemView.cv_people.setOnClickListener {
            val add = Intent(context, DetailsPeopleActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(AppConstants.BUNDLE_PEOPLE, peopleModel)
            add.putExtras(bundle)
            context.startActivity(add)
        }
    }

}