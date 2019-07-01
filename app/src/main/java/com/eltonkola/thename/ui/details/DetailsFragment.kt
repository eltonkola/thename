package com.eltonkola.thename.ui.details

import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.db.chart.animation.Animation
import com.db.chart.model.LineSet
import com.db.chart.renderer.AxisRenderer
import com.db.chart.tooltip.Tooltip
import com.db.chart.util.Tools
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


//        thumbUp.setOnClickListener {
//            viewModel.thumbUp()
//        }
//
//        thumbDown.setOnClickListener {
//            viewModel.thumbDown()
//        }
//
//        fav.setOnClickListener {
//            viewModel.fav()
//        }
//
//        star.setOnClickListener {
//            viewModel.star()
//        }

//
        viewModel.emri.observe(this, Observer {
            Timber.d("render data $id")
            renderEmri(it)
        })

    }


    private fun renderEmri(emri: Emri) {
        emri_txt.text = emri.name
        gjinia_txt.text = if (emri.male) "M" else "F"
        frekuenca_txt.text = emri.frequency.toString()
        yeras_txt.text = "TODO - info"


        val chartData = emri.getPerYerData()


//        val mLabels = chartData.keys.toTypedArray()
//
//        val mValues = arrayOf(
//            chartData.keys.map { it.toFloat() }.toFloatArray(),
//            chartData.values.map { it.toFloat() }.toFloatArray()
//        )

        //chart
        val barSet = LineSet(chartData.keys.toTypedArray(), chartData.values.map { it.toFloat() }.toFloatArray())

        val mLabels = arrayOf("A", "B", "C", "D")

        val mValues = arrayOf(floatArrayOf(6.5f, 8.5f, 2.5f, 10f), floatArrayOf(7.5f, 3.5f, 5.5f, 4f))

        // Data
//        val barSet = LineSet(mLabels, mValues[0])
        barSet.color = Color.parseColor("#fc2a53")
        chart.addData(barSet)

//        val order = intArrayOf(1, 0, 2, 3)
        val chartOneAction = Runnable {
            showTooltip()
        }

        chart.setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
            .setYLabels(AxisRenderer.LabelPosition.NONE)
            .show(
                Animation()
                    //.inSequence(.5f, order)
                    .withEndAction(chartOneAction)
            )


//        mChart.dismissAllTooltips()
//        if (firstStage)
//            mChart.updateValues(0, mValues[1])
//        else
//            mChart.updateValues(0, mValues[0])
//        mChart.notifyDataUpdate()


//        mChart.dismissAllTooltips()
//        val order = intArrayOf(0, 2, 1, 3)
//        mChart.dismiss(Animation().inSequence(.5f, order).withEndAction(action))


//        if (emri.thumb == 1) {
//            thumbDown.setBackgroundColor(Color.BLACK)
//        } else {
//            thumbDown.setBackgroundColor(Color.GRAY)
//        }
//
//
//        if (emri.thumb == 2) {
//            thumbUp.setBackgroundColor(Color.GREEN)
//        } else {
//            thumbUp.setBackgroundColor(Color.GRAY)
//        }
//
//        if (emri.favorite) {
//            fav.setBackgroundColor(Color.RED)
//        } else {
//            fav.setBackgroundColor(Color.GRAY)
//        }
//
//
//        if (viewModel.isStar()) {
//            star.setBackgroundColor(Color.YELLOW)
//        } else {
//            star.setBackgroundColor(Color.GRAY)
//        }


    }

    private fun showTooltip() {

        // Tooltip
        val tip = Tooltip(context, R.layout.square_tooltip)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            tip.setEnterAnimation(
                PropertyValuesHolder.ofFloat(View.ALPHA, 1.toFloat()),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
            ).duration = 200

            tip.setExitAnimation(
                PropertyValuesHolder.ofFloat(View.ALPHA, 0.toFloat()),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f)
            ).duration = 200
        }
        tip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP)
        tip.setDimensions(Tools.fromDpToPx(25f).toInt(), Tools.fromDpToPx(25f).toInt())
        tip.setMargins(0, 0, 0, Tools.fromDpToPx(10f).toInt())
        tip.prepare(chart.getEntriesArea(0).get(2), 0f)

        chart.showTooltip(tip, true)
    }


}
