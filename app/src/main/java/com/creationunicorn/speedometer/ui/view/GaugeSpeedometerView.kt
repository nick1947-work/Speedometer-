package com.creationunicorn.speedometer.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.creationunicorn.speedometer.R
import kotlin.math.cos
import kotlin.math.sin

class GaugeSpeedometerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val arcRect = RectF()
    private val startAngle = 135f
    private val sweepAngle = 270f

    private var maxSpeed = 140f
    var currentSpeed = 0f
        set(value) {
            field = value.coerceIn(0f, maxSpeed)
            invalidate()
        }

    private val bgArcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 24f
        strokeCap = Paint.Cap.ROUND
        color = Color.parseColor("#1B1E26")
    }

    private val activeArcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 14f
        strokeCap = Paint.Cap.ROUND
    }

    private val tickPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = ContextCompat.getColor(context, R.color.neon_cyan)
    }

    private val tickTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#B0B7C3")
        textSize = 28f
        textAlign = Paint.Align.CENTER
    }

    private val needlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        strokeCap = Paint.Cap.ROUND
        color = ContextCompat.getColor(context, R.color.neon_cyan)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val pad = 44f
        val diameter = minOf(w, h) - (pad * 2)
        val left = (w - diameter) / 2f
        val top = (h - diameter) / 2f
        arcRect.set(left, top, left + diameter, top + diameter)

        // Gradient for active arc: Cyan -> Orange -> Red
        val colors = intArrayOf(
            Color.parseColor("#00FFF2"),
            Color.parseColor("#00FFF2"),
            Color.parseColor("#FF7A00"),
            Color.parseColor("#FF3B30"),
            Color.parseColor("#00FFF2")
        )
        val positions = floatArrayOf(0f, 0.35f, 0.7f, 0.85f, 1f)
        activeArcPaint.shader = SweepGradient(w / 2f, h / 2f, colors, positions)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2f
        val cy = height / 2f
        val radius = arcRect.width() / 2f

        // 1. Background Arc
        canvas.drawArc(arcRect, startAngle, sweepAngle, false, bgArcPaint)

        // 2. Active Progress Arc
        val progressFraction = (currentSpeed / maxSpeed).coerceIn(0.01f, 1f)
        canvas.drawArc(arcRect, startAngle, sweepAngle * progressFraction, false, activeArcPaint)

        // 3. Ticks and Labels around perimeter
        val tickCount = 8
        for (i in 0..tickCount) {
            val fraction = i.toFloat() / tickCount
            val angleDeg = startAngle + (fraction * sweepAngle)
            val angleRad = Math.toRadians(angleDeg.toDouble())

            val innerR = radius - 18f
            val outerR = radius - 4f
            val startX = (cx + innerR * cos(angleRad)).toFloat()
            val startY = (cy + innerR * sin(angleRad)).toFloat()
            val endX = (cx + outerR * cos(angleRad)).toFloat()
            val endY = (cy + outerR * sin(angleRad)).toFloat()

            canvas.drawLine(startX, startY, endX, endY, tickPaint)

            // Tick number label
            val labelR = radius + 24f
            val labelX = (cx + labelR * cos(angleRad)).toFloat()
            val labelY = (cy + labelR * sin(angleRad) + 10f).toFloat()
            val labelVal = (fraction * maxSpeed).toInt()
            canvas.drawText("$labelVal", labelX, labelY, tickTextPaint)
        }

        // 4. Pointer / Needle indicator
        val needleAngleRad = Math.toRadians((startAngle + (progressFraction * sweepAngle)).toDouble())
        val needleInnerR = radius - 55f
        val needleOuterR = radius - 18f
        val nStartX = (cx + needleInnerR * cos(needleAngleRad)).toFloat()
        val nStartY = (cy + needleInnerR * sin(needleAngleRad)).toFloat()
        val nEndX = (cx + needleOuterR * cos(needleAngleRad)).toFloat()
        val nEndY = (cy + needleOuterR * sin(needleAngleRad)).toFloat()
        canvas.drawLine(nStartX, nStartY, nEndX, nEndY, needlePaint)
    }

    fun setSpeedMax(max: Float) {
        maxSpeed = if (max > 0) max else 140f
        invalidate()
    }
}
