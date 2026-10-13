package com.creationunicorn.speedometer.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.creationunicorn.speedometer.R

class DigitalDisplayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val digitScale = 0.7125f
    private val first1Scale = 0.218f
    private val colonScale = 0.165f
    private val dotScale = 0.153f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dstRect = Rect()

    var digitalColor: Int = ContextCompat.getColor(context, R.color.neon_cyan)
        set(value) {
            field = value
            paint.colorFilter = PorterDuffColorFilter(value, PorterDuff.Mode.SRC_IN)
            invalidate()
        }

    var text: String = "0"
        set(value) {
            if (field != value) {
                val oldLength = field.length
                val oldFirstIsOne = field.startsWith("1")
                field = value
                if (oldLength != value.length || oldFirstIsOne != value.startsWith("1")) {
                    requestLayout()
                }
                invalidate()
            }
        }

    init {
        paint.colorFilter = PorterDuffColorFilter(digitalColor, PorterDuff.Mode.SRC_IN)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val targetHeight = if (heightMode == MeasureSpec.EXACTLY || (heightMode == MeasureSpec.AT_MOST && heightSize > 0)) {
            heightSize
        } else {
            (24 * resources.displayMetrics.density).toInt()
        }

        var totalWidth = 0
        for (i in text.indices) {
            val c = text[i]
            val w = when {
                c == ':' -> (colonScale * targetHeight).toInt()
                c == '.' -> (dotScale * targetHeight).toInt()
                c == '1' && i == 0 -> (first1Scale * targetHeight).toInt()
                c in '0'..'9' -> (digitScale * targetHeight).toInt()
                else -> (digitScale * targetHeight * 0.5f).toInt()
            }
            totalWidth += w
        }

        val finalWidth = resolveSize(totalWidth, widthMeasureSpec)
        val finalHeight = resolveSize(targetHeight, heightMeasureSpec)
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (text.isEmpty() || height <= 0) return

        val h = height
        var currentX = 0

        for (i in text.indices) {
            val c = text[i]
            val charWidth = when {
                c == ':' -> (colonScale * h).toInt()
                c == '.' -> (dotScale * h).toInt()
                c == '1' && i == 0 -> (first1Scale * h).toInt()
                c in '0'..'9' -> (digitScale * h).toInt()
                else -> (digitScale * h * 0.5f).toInt()
            }

            val bitmap = getBitmapForChar(c, i == 0)
            if (bitmap != null) {
                dstRect.set(currentX, 0, currentX + charWidth, h)
                canvas.drawBitmap(bitmap, null, dstRect, paint)
            }
            currentX += charWidth
        }
    }

    private fun getBitmapForChar(c: Char, isFirst: Boolean): Bitmap? {
        val resId = when {
            c == ':' -> R.drawable.ic_icon_number_colon
            c == '.' -> R.drawable.ic_icon_number_point
            c == '1' && isFirst -> R.drawable.ic_icon_number_1_first
            c == '0' -> R.drawable.ic_icon_number_0
            c == '1' -> R.drawable.ic_icon_number_1
            c == '2' -> R.drawable.ic_icon_number_2
            c == '3' -> R.drawable.ic_icon_number_3
            c == '4' -> R.drawable.ic_icon_number_4
            c == '5' -> R.drawable.ic_icon_number_5
            c == '6' -> R.drawable.ic_icon_number_6
            c == '7' -> R.drawable.ic_icon_number_7
            c == '8' -> R.drawable.ic_icon_number_8
            c == '9' -> R.drawable.ic_icon_number_9
            else -> return null
        }
        return getCachedBitmap(context, resId)
    }

    companion object {
        private val bitmapCache = HashMap<Int, Bitmap>()

        private fun getCachedBitmap(context: Context, resId: Int): Bitmap? {
            var bmp = bitmapCache[resId]
            if (bmp == null || bmp.isRecycled) {
                val d = ContextCompat.getDrawable(context, resId) ?: return null
                val width = if (d.intrinsicWidth > 0) d.intrinsicWidth else 60
                val height = if (d.intrinsicHeight > 0) d.intrinsicHeight else 90
                bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bmp)
                d.setBounds(0, 0, canvas.width, canvas.height)
                d.draw(canvas)
                bitmapCache[resId] = bmp
            }
            return bmp
        }
    }
}
