package com.lion.komvvm.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.ShapeAppearancePathProvider
import com.lion.komvvm.R

/**
 * diy view: clip the view
 * 1. set the attribute in styles.xml
 * 2. use the attribute to calculate the path
 * 3. clip path
 */
class MaskedCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyle) {
    private val pathProvider = ShapeAppearancePathProvider()
    private val path = Path()
    private val shapeAppearance: ShapeAppearanceModel = ShapeAppearanceModel(
        context,
        attrs,
        defStyle,
        R.style.Widget_MaterialComponents_CardView
    )
    private val rectF = RectF(0f, 0f, 0f, 0f)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        rectF.right = w.toFloat()
        rectF.bottom = h.toFloat()
        pathProvider.calculatePath(shapeAppearance, 1f, rectF, path)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }
}