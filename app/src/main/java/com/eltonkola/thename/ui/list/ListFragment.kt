package com.eltonkola.thename.ui.list

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
import com.eltonkola.adapterz_lib.ViewRenderZ
import com.eltonkola.thename.R
import com.eltonkola.thename.model.db.Emri
import com.eltonkola.thename.ui.explore.ExploreViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    private val viewModel: ExploreViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = AdapterZ()//pool
        adapter.addRenderer(ViewRenderZ<Emri>(R.layout.row_emri) { vh, model: Emri ->
            val title_header = vh.itemView.findViewById<TextView>(R.id.emri_vlera)
            title_header.setText(model.name + " - " + model.male.toString() + " - " + model.frequency.toString())
            vh.itemView.setOnClickListener {
                val action = ListFragmentDirections.actionListToDetails(model)
                findNavController().navigate(action)
            }
        })
//        adapter.addRenderer(headerRenderer())

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel.dataList.observe(this, Observer { list ->
            adapter.submitList(list)
        })


        viewModel.loadData()

//
//
//        contacts.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_contactsFragment)
//        }

    }
}
