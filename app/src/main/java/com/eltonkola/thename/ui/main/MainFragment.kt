package com.eltonkola.thename.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eltonkola.adapterz_lib.AdapterZ
import com.eltonkola.adapterz_lib.CompositeViewRenderZ
import com.eltonkola.adapterz_lib.ViewRenderZ
import com.eltonkola.thename.R
import com.eltonkola.thename.model.*
import com.eltonkola.thename.ui.list.ListViewModel
import com.eltonkola.thename.utils.HorizontalGridDecoration
import com.eltonkola.thename.utils.formatFrequency
import com.eltonkola.thename.utils.visibility
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


//
//        appBar.setLogo(R.mipmap.ic_launcher)
//        appBar.title = "Pinko Palino"

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

            progress.visibility = list.isEmpty().visibility
            content_main.visibility = list.isNotEmpty().visibility

            adapter.submitList(list)
        })


//        MaterialAlertDialogBuilder(context)
//            .setTitle("Title")
//            .setMessage("Message")
//            .setPositiveButton("Ok", null)
//            .show()


//        toggle_button_group.addOnButtonCheckedListener { group, checkedId, isChecked ->
//            toast("Selected: $checkedId")
//        }
    }


    private fun bindFavoritedItem(): CompositeViewRenderZ<FavoritedItem> {
        return CompositeViewRenderZ(
            R.layout.main_row_favorites, { vh, model ->

                vh.itemView.findViewById<TextView>(R.id.container_icon).setOnClickListener {

                    val action =
                        MainFragmentDirections.actionMainToListAll(ListViewModel.Shfleto.FAVORITES.vlera)
                    findNavController().navigate(action)

                }


            },
            R.id.childRecycler
        ) { recycler ->

            val nrRows = 3
            recycler.layoutManager =
                GridLayoutManager(context, nrRows, GridLayoutManager.HORIZONTAL, false)
            recycler.setHasFixedSize(true)
            val pagePadding = context!!.resources.getDimension(R.dimen.main_padding)
            val itemPadding = context!!.resources.getDimension(R.dimen.main_gutter)
            recycler.addItemDecoration(
                HorizontalGridDecoration(
                    pagePadding.toInt(),
                    itemPadding.toInt(),
                    nrRows
                )
            )
        }
    }

    private fun bindFavoriteItem(): ViewRenderZ<FavoriteItem> {
        return ViewRenderZ(R.layout.main_row_favorite) { vh, model ->
            val title_header = vh.itemView.findViewById<TextView>(R.id.emri_vlera)
            title_header.setText(model.emri.name)
            vh.itemView.setOnClickListener {
                val action = MainFragmentDirections.actionMainToDetails(model.emri)
                findNavController().navigate(action)
            }
        }
    }

    private fun bindExploreItem(): ViewRenderZ<ExploreItem> {
        return ViewRenderZ(R.layout.main_row_explore) { vh, model ->
            val mNr = vh.itemView.findViewById<TextView>(R.id.mNr)
            val fNr = vh.itemView.findViewById<TextView>(R.id.fNr)
            val nrTotal = vh.itemView.findViewById<TextView>(R.id.nrTotal)
            mNr.setText(model.mCount.formatFrequency())
            fNr.setText(model.fCount.formatFrequency())

            val tot = model.fCount + model.mCount

            nrTotal.setText(tot.formatFrequency() + " names to explore")
            fNr.setText(model.fCount.formatFrequency())
            mNr.setText(model.mCount.formatFrequency())

            vh.itemView.setOnClickListener {
                findNavController().navigate(R.id.action_main_to_explore)
            }
        }
    }


    private fun bindListAllItem(): ViewRenderZ<ListAllItem> {
        return ViewRenderZ(R.layout.main_row_list_all) { vh, _ ->

            vh.itemView.setOnClickListener {
                val action =
                    MainFragmentDirections.actionMainToListAll(ListViewModel.Shfleto.ALL.vlera)
                findNavController().navigate(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMenu()
    }


    private fun bindThumbedListItem(): CompositeViewRenderZ<ThumbedListItem> {
        return CompositeViewRenderZ(
            R.layout.main_row_thumbed, { vh, _ ->

                vh.itemView.findViewById<TextView>(R.id.container_icon).setOnClickListener {

                    val action =
                        MainFragmentDirections.actionMainToListAll(ListViewModel.Shfleto.THUMBED.vlera)
                    findNavController().navigate(action)

                }


            },
            R.id.childRecycler
        ) { recycler ->
            val nrRows = 1
            recycler.layoutManager =
                GridLayoutManager(context, nrRows, GridLayoutManager.HORIZONTAL, false)
            recycler.setHasFixedSize(true)
            val pagePadding = context!!.resources.getDimension(R.dimen.main_padding)
            val itemPadding = context!!.resources.getDimension(R.dimen.main_gutter)
            recycler.addItemDecoration(
                HorizontalGridDecoration(
                    pagePadding.toInt(),
                    itemPadding.toInt(),
                    nrRows
                )
            )
        }
    }

    private fun bindThumbedSubListItem(): CompositeViewRenderZ<ThumbedSubListItem> {
        return CompositeViewRenderZ(
            R.layout.main_row_thumbed_list, { _, _ -> },
            R.id.childRecyclerList
        ) { recycler ->
            recycler.layoutManager =
                LinearLayoutManager(recycler.context, RecyclerView.VERTICAL, false)
            recycler.setHasFixedSize(true)
        }
    }


    private fun bindThumbedSubListElementItem(): ViewRenderZ<ThumbedSubListElementItem> {
        return ViewRenderZ(R.layout.main_row_thumbed_emri) { vh, model ->
            val title_header = vh.itemView.findViewById<TextView>(R.id.emri_vlera)
            val emri_frequency = vh.itemView.findViewById<TextView>(R.id.emri_frequency)

            title_header.setText(model.emri.name)
            emri_frequency.setText(model.emri.frequency.formatFrequency())

            vh.itemView.setOnClickListener {
                val action = MainFragmentDirections.actionMainToDetails(model.emri)
                findNavController().navigate(action)
            }
        }
    }

}
