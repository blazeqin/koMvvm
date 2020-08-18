package com.lion.komvvm.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.properties.Delegates

/**
 * 圆角或者圆形图片
 */
class CircleImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attributeSet, defStyle) {
    private val mRectF = RectF(0f, 0f, 0f, 0f)
    var isCircle = true
    //显示重叠部分
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private val path = Path()
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mRadius = 0f
    private var mRadii by Delegates.notNull<FloatArray>()
    private val corner = 26f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mRectF.right = w.toFloat()
        mRectF.bottom = h.toFloat()
        mRadius = Math.min(w/2f, h/2f)
        mRadii = floatArrayOf(corner,corner,0f,0f,corner,corner,0f,0f)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        //save
        canvas.saveLayer(mRectF,null)
        //draw
        super.onDraw(canvas)
        //drawcircle
        if (isCircle) {
            path.addCircle(mRadius,mRadius,mRadius,Path.Direction.CCW)
        }else{
            path.addRoundRect(mRectF, mRadii, Path.Direction.CCW)
        }
        mPaint.style = Paint.Style.FILL
        mPaint.xfermode = xfermode
        //draw path
        canvas.drawPath(path, mPaint)
        mPaint.xfermode = null
        //restore
        canvas.restore()
    }
}