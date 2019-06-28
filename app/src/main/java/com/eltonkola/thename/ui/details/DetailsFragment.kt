package com.eltonkola.thename.ui.details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.eltonkola.thename.R
import com.eltonkola.thename.model.db.Emri
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private val _emri: Emri by lazy { args.emri }

    private val viewModel: DetailsViewModel by viewModel { parametersOf(_emri) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        thumbUp.setOnClickListener {
            viewModel.thumbUp()
        }

        thumbDown.setOnClickListener {
            viewModel.thumbDown()
        }

        fav.setOnClickListener {
            viewModel.fav()
        }

        star.setOnClickListener {
            viewModel.star()
        }

//
        viewModel.emri.observe(this, Observer {
            Timber.d("render data $id")
            renderEmri(it)
        })

    }

    private fun renderEmri(emri: Emri) {
        emri_txt.text = emri.name
        gjinia_txt.text = emri.male.toString()
        frekuenca_txt.text = emri.frequency.toString()
        yeras_txt.text = emri.peryear

        if (emri.thumb == 1) {
            thumbDown.setBackgroundColor(Color.BLACK)
        } else {
            thumbDown.setBackgroundColor(Color.GRAY)
        }


        if (emri.thumb == 2) {
            thumbUp.setBackgroundColor(Color.GREEN)
        } else {
            thumbUp.setBackgroundColor(Color.GRAY)
        }

        if (emri.favorite) {
            fav.setBackgroundColor(Color.RED)
        } else {
            fav.setBackgroundColor(Color.GRAY)
        }


        if (viewModel.isStar()) {
            star.setBackgroundColor(Color.YELLOW)
        } else {
            star.setBackgroundColor(Color.GRAY)
        }


    }

}
