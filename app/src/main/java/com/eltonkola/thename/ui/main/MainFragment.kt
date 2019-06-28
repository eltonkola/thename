package com.eltonkola.thename.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eltonkola.adapterz_lib.AdapterZ
import com.eltonkola.adapterz_lib.CompositeViewRenderZ
import com.eltonkola.adapterz_lib.ViewRenderZ
import com.eltonkola.thename.R
import com.eltonkola.thename.model.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = AdapterZ()
        //fav horizontal scroll
        adapter.addRenderer(bindFavoritedItem().apply {
            addRenderer(bindFavoriteItem())
        })
        //explore
        adapter.addRenderer(bindExploreItem())

        //thumbed
        adapter.addRenderer(bindThumbedListItem().apply {
            addRenderer(bindThumbedSubListItem().apply {
                addRenderer(bindThumbedSubListElementItem())
            })
        })

        //list all
        adapter.addRenderer(bindListAllItem())

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel.menu.observe(this, Observer { list ->
            adapter.submitList(list)
        })


//        MaterialAlertDialogBuilder(context)
//            .setTitle("Title")
//            .setMessage("Message")
//            .setPositiveButton("Ok", null)
//            .show()

    }

    private fun bindFavoritedItem(): CompositeViewRenderZ<FavoritedItem> {
        return CompositeViewRenderZ(
            R.layout.row_favorites, { _, _ -> },
            R.id.childRecycler
        ) { recycler ->

        }
    }

    private fun bindFavoriteItem(): ViewRenderZ<FavoriteItem> {
        return ViewRenderZ(R.layout.row_favorite) { vh, model ->
            val title_header = vh.itemView.findViewById<TextView>(R.id.emri_vlera)
            title_header.setText(model.emri.name)
            vh.itemView.setOnClickListener {
                val action = MainFragmentDirections.actionMainToDetails(model.emri)
                findNavController().navigate(action)
            }
        }
    }

    private fun bindExploreItem(): ViewRenderZ<ExploreItem> {
        return ViewRenderZ(R.layout.row_explore) { vh, model ->
            val mNr = vh.itemView.findViewById<TextView>(R.id.mNr)
            val fNr = vh.itemView.findViewById<TextView>(R.id.fNr)
            mNr.setText(model.mCount.toString())
            fNr.setText(model.fCount.toString())
            vh.itemView.setOnClickListener {
                findNavController().navigate(R.id.action_main_to_explore)
            }
        }
    }


    private fun bindListAllItem(): ViewRenderZ<ListAllItem> {
        return ViewRenderZ(R.layout.row_list_all) { vh, _ ->
            vh.itemView.setOnClickListener {
                findNavController().navigate(R.id.action_main_to_list_all)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMenu()
    }


    private fun bindThumbedListItem(): CompositeViewRenderZ<ThumbedListItem> {
        return CompositeViewRenderZ(
            R.layout.row_thumbed, { _, _ -> },
            R.id.childRecycler
        ) { recycler ->

        }
    }

    private fun bindThumbedSubListItem(): CompositeViewRenderZ<ThumbedSubListItem> {
        return CompositeViewRenderZ(
            R.layout.row_thumbed_list, { _, _ -> },
            R.id.childRecyclerList
        ) { recycler ->
            recycler.layoutManager = LinearLayoutManager(recycler.context, RecyclerView.VERTICAL, false)
            recycler.setHasFixedSize(true)
        }
    }


    private fun bindThumbedSubListElementItem(): ViewRenderZ<ThumbedSubListElementItem> {
        return ViewRenderZ(R.layout.row_thumbed_emri) { vh, model ->
            val title_header = vh.itemView.findViewById<TextView>(R.id.emri_vlera)
            title_header.setText(model.emri.name)
            vh.itemView.setOnClickListener {
                val action = MainFragmentDirections.actionMainToDetails(model.emri)
                findNavController().navigate(action)
            }
        }
    }

}