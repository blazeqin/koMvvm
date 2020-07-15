package com.lion.komvvm.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.lion.komvvm.R

class LineItemDecoration(private val context:Context) : RecyclerView.ItemDecoration() {
    private val mPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    //the pxValue cannot be too small, because it is px. at least it has 1dp.
    private val mDivider = SizeUtils.px2dp(3f)

    init {
        mPaint.setColor(context.resources.getColor(R.color.grey))
    }

    /**
     * set offsets about itemView
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0,0,0,mDivider)
    }

    /**
     * draw before draw item view. so this will below to item view.
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            c.drawRect(child.left.toFloat(),child.bottom.toFloat(),child.right.toFloat(),
                (child.bottom + mDivider).toFloat(),mPaint)
        }
    }
}