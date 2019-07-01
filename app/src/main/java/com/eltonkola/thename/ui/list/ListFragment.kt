package com.eltonkola.thename.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eltonkola.thename.R
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    private val args: ListFragmentArgs by navArgs()
    private val qyeryType: ListViewModel.Shfleto by lazy { ListViewModel.Shfleto.getType(args.qyeryType.toString()) }

    private val viewModel: ListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = EmriListAdapter(context!!)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel.allList.observe(this, Observer { list ->
//            if (list.isEmpty()) {
//                progress.visibility = View.GONE
//            } else {
//                progress.visibility = View.VISIBLE
//            }


            adapter.submitList(list)
        })

        //query them all
        viewModel.loadData(qyeryType)

    }
}
