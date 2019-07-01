package com.eltonkola.thename.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalGridDecoration(private val pagePadding: Int,
                               private val itemPadding: Int,
                               private val rows: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        val itemCount = state.itemCount
       
        //first two elements have the page padding on the left, and
        if(itemPosition in 0..(rows-1)){
            outRect.left = pagePadding
        }else{
            outRect.left = itemPadding
        }

        if(itemPosition in (itemCount-rows)..itemCount){
            outRect.right = pagePadding
        }

    }
}