package com.github.gunin_igor75.androidsdktask.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.github.gunin_igor75.androidsdktask.R
import kotlin.properties.Delegates


class CustomView(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int,
) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
            this(context, attributeSet, defStyleAttr, R.style.DefaultCustomViewStyle)

    constructor(context: Context, attributeSet: AttributeSet?) :
            this(context, attributeSet, R.attr.CustomViewStyle)

    constructor(context: Context) : this(context, null)

    private var colorBorder by Delegates.notNull<Int>()

    private lateinit var borderPaint: Paint

    private val rect = RectF(0f, 0f, 0f, 0f)

    init {
        if (attributeSet != null) {
            initAttributes(attributeSet, defStyleAttr, defStyleRes)
        } else {
            initDefaultAttributes()
        }
        initPaints()
    }

    private fun initAttributes(attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.CustomView,
            defStyleAttr,
            defStyleRes
        )
        colorBorder = typedArray.getColor(R.styleable.CustomView_colorBorder, DEFAULT_COLOR_BORDER)
        typedArray.recycle()
    }

    private fun initDefaultAttributes() {
        colorBorder = DEFAULT_COLOR_BORDER
    }

    private fun initPaints() {
        borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint.color = colorBorder
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, resources.displayMetrics)
    }

    companion object {
        private const val DEFAULT_COLOR_BORDER = Color.GREEN
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = suggestedMinimumWidth + paddingStart + paddingEnd
        val minHeight = suggestedMinimumHeight + paddingTop + paddingBottom
        setMeasuredDimension(
            resolveSize(minWidth, widthMeasureSpec),
            resolveSize(minHeight, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        rect.bottom = height.toFloat()
        rect.right = width.toFloat()

        canvas.drawRect(rect, borderPaint)
    }
}