package com.github.gunin_igor75.androidsdktask.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.os.bundleOf
import com.github.gunin_igor75.androidsdktask.R
import com.github.gunin_igor75.androidsdktask.utils.getParcelableProvider
import java.util.Random
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

    private lateinit var fillPaint: Paint

    private val random = Random()

    private var countAttempt: Int = 0

    private val rect = RectF(0f, 0f, 0f, 0f)

    private val rectCountAttempt = RectF(0f, 0f, 0f, 0f)

    init {
        if (attributeSet != null) {
            initAttributes(attributeSet, defStyleAttr, defStyleRes)
        } else {
            initDefaultAttributes()
        }
        initPaints()
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

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return bundleOf(
            KEY_COUNT_ATTEMPT to countAttempt,
            KEY_SUPER_STATE to superState
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as Bundle
        val saveCountAttempt = bundle.getInt(KEY_COUNT_ATTEMPT)
        countAttempt = saveCountAttempt
        super.onRestoreInstanceState(bundle.getParcelableProvider(KEY_SUPER_STATE))
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

        fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        fillPaint.color = getRandomColor()
        fillPaint.style = Paint.Style.FILL
    }

    private fun setupNextRect(width: Float, height: Float) {
        val proportion = countAttempt.toFloat() / 10
        val currentWidth = proportion * width
        val currentHeight = proportion * height
        rectCountAttempt.right = currentWidth
        rectCountAttempt.bottom = currentHeight
    }

    private fun getRandomColor(): Int {
        return Color.rgb(
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )
    }

//    private fun drawRandomRectFill(canvas: Canvas) {
//        repeat(countAttempt){ index ->
//            canvas.drawDoubleRoundRect(
//
//            )
//        }
//    }

    companion object {
        private const val DEFAULT_COLOR_BORDER = Color.GREEN
        private const val KEY_COUNT_ATTEMPT = "key_count_attempt"
        private const val KEY_SUPER_STATE = "key_super_state"
    }
}
