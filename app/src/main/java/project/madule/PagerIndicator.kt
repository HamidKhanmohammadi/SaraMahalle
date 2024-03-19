package project.madule

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ImageView
import project.main.G

@SuppressLint("AppCompatCustomView")
class PagerIndicator : ImageView {
  private var fillPaint: Paint? = null
  private var strokPaint: Paint? = null
  private var count: Int = 0
  private var currentPageIndex: Int = 0
  private var screenWidth: Int = 0
  private var indicatorWidth: Int = 0
  private var offsetX: Float = 0.toFloat()
  private var percent: Float = 0.toFloat()

  private val CIRCLE_RADIUS = 13
  private val CIRCLE_SPACE = 25
  private val CIRCLE_STROKE_COLOR = Color.GRAY
  private val CIRCLE_FILL_COLOR = Color.argb(225, 200, 250, 220)


  constructor(context: Context) : super(context) {
    initialize()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    initialize()
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    initialize()
  }

  private fun initialize() {
    fillPaint = Paint()
    fillPaint!!.style = Paint.Style.FILL
    fillPaint!!.color = CIRCLE_FILL_COLOR
    fillPaint!!.isAntiAlias = true

    strokPaint = Paint()
    strokPaint!!.style = Paint.Style.STROKE
    strokPaint!!.color = CIRCLE_STROKE_COLOR
    strokPaint!!.isAntiAlias = true

    screenWidth = G.context.resources.displayMetrics.widthPixels

  }

  fun setIndicatorsCount(value: Int) {
    count = value
    computeIndicatorWidth()
  }

  fun setCurrentPage(value: Int) {
    currentPageIndex = value
    invalidate()
  }

  fun setPercent(percent: Float) {
    this.percent = percent
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.apply {
      for (i in 0 until count) {
        val paint = strokPaint
        canvas.drawCircle(offsetX + i * (CIRCLE_RADIUS + CIRCLE_SPACE), 15f, CIRCLE_RADIUS.toFloat(), strokPaint!!)

        var canDrawFill = false
        if (i == currentPageIndex) {
          fillPaint!!.alpha = ((1.0f - percent) * 255).toInt()
          canDrawFill = true
        }

        if (percent > 0) {
          if (i == currentPageIndex + 1) {
            fillPaint!!.alpha = (percent * 255).toInt()
            canDrawFill = true
          }
        }

        if (canDrawFill) {
          canvas.drawCircle(offsetX + i * (CIRCLE_RADIUS + CIRCLE_SPACE), 15f, CIRCLE_RADIUS.toFloat(), fillPaint!!)
        }
      }
    }
  }

  private fun computeIndicatorWidth() {
    indicatorWidth = count * (CIRCLE_RADIUS + CIRCLE_SPACE)
    offsetX = (screenWidth - indicatorWidth).toFloat() / 2
  }
}
