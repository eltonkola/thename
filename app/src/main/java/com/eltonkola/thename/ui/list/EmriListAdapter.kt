package com.eltonkola.thename.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eltonkola.thename.R
import com.eltonkola.thename.model.db.Emri
import kotlinx.android.synthetic.main.row_emri.view.*

class EmriListAdapter(val context: Context) :
    PagedListAdapter<Emri, EmriViewHolder>(EmriDiffCallback()) {

    override fun onBindViewHolder(holderPerson: EmriViewHolder, position: Int) {
        var person = getItem(position)

        if (person == null) {
            holderPerson.clear()
        } else {
            holderPerson.bind(person)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmriViewHolder {
        return EmriViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_emri,
                parent, false
            )
        )
    }

}

class EmriDiffCallback : DiffUtil.ItemCallback<Emri>() {

    override fun areContentsTheSame(oldItem: Emri, newItem: Emri): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Emri, newItem: Emri): Boolean {
        return oldItem.name == newItem.name
    }
}


class EmriViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var tvName: TextView = view.emri_vlera

    fun bind(emri: Emri) {
        tvName.text = emri.name + " - " + emri.frequency + " - " + emri.male + " - " + emri.isMale()
    }

    fun clear() {
        tvName.text = null
    }

}
