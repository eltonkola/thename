package com.eltonkola.thename.ui.explore


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eltonkola.thename.R
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.ui.list.EmriDiffCallback
import com.eltonkola.thename.utils.formatFrequency
import com.google.android.material.card.MaterialCardView

class CardStackAdapter(
    context: Context,
    private val mbiemri: String,
    private var emrat: List<Emri> = emptyList(),
    private val onCardClick: (Emri) -> Any
) : PagedListAdapter<Emri, ViewHolderX>(EmriDiffCallback()) {

    private val boyColor = ContextCompat.getColor(context, R.color.color_boy)
    private val girlColor = ContextCompat.getColor(context, R.color.color_girl)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderX {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderX(inflater.inflate(R.layout.explore_row_explore, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderX, position: Int) {
        val emriElem = emrat[position]
        holder.emri_vlera.text = emriElem.name
        holder.emri_mbiemri.text = mbiemri
        holder.emri_count.text = emriElem.frequency.formatFrequency()

        if (emriElem.male) {
            holder.card_root.setCardBackgroundColor(boyColor)
        } else {
            holder.card_root.setCardBackgroundColor(girlColor)
        }

        holder.itemView.setOnClickListener { _ ->
            onCardClick.invoke(emriElem)
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
    var emri_mbiemri: TextView = view.findViewById(R.id.emri_mbiemri)
    var emri_count: TextView = view.findViewById(R.id.emri_count)
    val card_root: MaterialCardView = view.findViewById(R.id.card_root)
}
