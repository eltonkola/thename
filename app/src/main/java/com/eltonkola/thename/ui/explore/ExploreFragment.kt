package com.eltonkola.thename.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.eltonkola.thename.R
import com.eltonkola.thename.ui.list.ListFragmentDirections
import com.eltonkola.thename.utils.toast
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.fragment_explore.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class ExploreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    private val viewModel: ExploreViewModel by viewModel()
    private val adapter = CardStackAdapter() {
        val action = ExploreFragmentDirections.actionExploreToDetails(it)
        findNavController().navigate(action)
    }

    private val listener = object : CardStackListener {

        var currentPosition = 0

        override fun onCardDisappeared(view: View?, position: Int) {
            Timber.d("onCardDisappeared $position")
        }

        override fun onCardDragging(direction: Direction?, ratio: Float) {
            Timber.d("onCardDragging $ratio $direction")
        }

        override fun onCardSwiped(direction: Direction?) {
            Timber.d("onCardDragging $direction")
            when (direction) {
                Direction.Left -> viewModel.thumbDown(currentPosition)
                Direction.Right -> viewModel.thumbUp(currentPosition)
                else -> toast("Ignore")
            }
        }

        override fun onCardCanceled() {
            Timber.d("onCardCanceled")
        }

        override fun onCardAppeared(view: View?, position: Int) {
            Timber.d("onCardAppeared $position")
            currentPosition = position
        }

        override fun onCardRewound() {
            Timber.d("onCardRewound")
        }

    }

    private val layoutManager by lazy { CardStackLayoutManager(context, listener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager.setStackFrom(StackFrom.None)
        layoutManager.setVisibleCount(3)
        layoutManager.setTranslationInterval(8.0f)
        layoutManager.setScaleInterval(0.95f)
        layoutManager.setSwipeThreshold(0.3f)
        layoutManager.setMaxDegree(20.0f)
        layoutManager.setDirections(Direction.HORIZONTAL)
        layoutManager.setCanScrollHorizontal(true)
        layoutManager.setCanScrollVertical(true)
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        layoutManager.setOverlayInterpolator(LinearInterpolator())



        cardStackView.layoutManager = layoutManager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }


        //CardStackView.swipe()


//        val setting = SwipeAnimationSetting.Builder()
//            .setDirection(Direction.Right)
//            .setDuration(Duration.Normal.duration)
//            .setInterpolator(AccelerateInterpolator())
//            .build()
//        layoutManager.setSwipeAnimationSetting(setting)


//        cardStackView.swipe()


        viewModel.dataList.observe(this, Observer { list ->
            toast("Loaded: ${list.size}")
            adapter.setData(list)
            adapter.notifyDataSetChanged()
        })



        skip_button.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        rewind_button.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            layoutManager.setRewindAnimationSetting(setting)
            cardStackView.rewind()
        }

        like_button.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }


    }
}
