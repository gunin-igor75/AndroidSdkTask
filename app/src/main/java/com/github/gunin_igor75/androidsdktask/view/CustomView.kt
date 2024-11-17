package com.github.gunin_igor75.androidsdktask.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
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

    private var currentColor: Int = -1

    private val random = Random()

    private var countAttempt: Int = 0

    private val outerRect = RectF(0f, 0f, 0f, 0f)

    private val innerRect = RectF(0f, 0f, 0f, 0f)

    private var isClicked: Boolean = false

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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        outerRect.bottom = height.toFloat()
        outerRect.right = width.toFloat()
        if (countAttempt == 0) {
            canvas.drawRect(outerRect, borderPaint)
        } else {
            drawCustomView(canvas, outerRect.width(), outerRect.height())
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return bundleOf(
            KEY_COUNT_ATTEMPT to countAttempt,
            KEY_CURRENT_COLOR to currentColor,
            KEY_SUPER_STATE to superState
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as Bundle
        val saveCountAttempt = bundle.getInt(KEY_COUNT_ATTEMPT)
        countAttempt = saveCountAttempt
        val saveCurrentColor = bundle.getInt(KEY_CURRENT_COLOR)
        currentColor = saveCurrentColor
        super.onRestoreInstanceState(bundle.getParcelableProvider(KEY_SUPER_STATE))
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }

            MotionEvent.ACTION_UP -> {
                return performClick()
            }
        }
        return false
    }

    override fun performClick(): Boolean {
        super.performClick()
        countAttempt = (++countAttempt % BORDER_COUNT_RECT)
        if (!isClicked) isClicked = true
        invalidate()
        return false
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
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                BORDER_STROKE,
                resources.displayMetrics
            )

        fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        setupRandomColor()
        fillPaint.color = currentColor
        fillPaint.style = Paint.Style.FILL
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun drawCustomView(canvas: Canvas, width: Float, height: Float) {
        setupNextRect(width, height)
        if (isClicked) setupRandomColor()
        fillPaint.color = currentColor
        canvas.drawDoubleRoundRect(
            outerRect,
            RADIUS_ROUND,
            RADIUS_ROUND,
            innerRect,
            RADIUS_ROUND,
            RADIUS_ROUND,
            fillPaint
        )
    }

    private fun setupNextRect(width: Float, height: Float) {
        val proportion = countAttempt.toFloat() / STEP_PROPORTION
        innerRect.top = outerRect.top + height * proportion / HALF_STEP_PROPORTION
        innerRect.left = outerRect.left + width * proportion / HALF_STEP_PROPORTION
        innerRect.right = outerRect.right - width * proportion / HALF_STEP_PROPORTION
        innerRect.bottom = outerRect.bottom - height * proportion / HALF_STEP_PROPORTION
    }

    private fun setupRandomColor() {
        currentColor = Color.rgb(
            random.nextInt(BORDER_COLOR),
            random.nextInt(BORDER_COLOR),
            random.nextInt(BORDER_COLOR)
        )
    }

    companion object {
        private const val DEFAULT_COLOR_BORDER = Color.GREEN
        private const val KEY_COUNT_ATTEMPT = "key_count_attempt"
        private const val KEY_CURRENT_COLOR = "key_current_color"
        private const val KEY_SUPER_STATE = "key_super_state"
        private const val RADIUS_ROUND = 0F
        private const val BORDER_COLOR = 256
        private const val STEP_PROPORTION = 10
        private const val HALF_STEP_PROPORTION = 2
        private const val BORDER_STROKE = 3F
        private const val BORDER_COUNT_RECT = 11
    }
}
