package com.eltonkola.thename.ui.explore


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eltonkola.thename.R
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.ui.list.EmriDiffCallback

class CardStackAdapter(
    private var emrat: List<Emri> = emptyList(),
    val onCardClick: (Emri) -> Any
) : PagedListAdapter<Emri, ViewHolderX>(EmriDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderX {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderX(inflater.inflate(R.layout.explore_row_explore, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderX, position: Int) {
        val spot = emrat[position]
        holder.emri_vlera.text = spot.name
        holder.emri_gjinia.text = spot.male.toString()
        holder.emri_count.text = spot.frequency.toString()

        holder.itemView.setOnClickListener { _ ->
            onCardClick.invoke(spot)
        }
    }

    override fun getItemCount(): Int {
        return emrat.size
    }

    fun setData(spots: List<Emri>) {
        this.emrat = spots
    }
}

class ViewHolderX(view: View) : RecyclerView.ViewHolder(view) {

    val emri_vlera: TextView = view.findViewById(R.id.emri_vlera)
    var emri_gjinia: TextView = view.findViewById(R.id.emri_gjinia)
    var emri_count: TextView = view.findViewById(R.id.emri_count)

}
