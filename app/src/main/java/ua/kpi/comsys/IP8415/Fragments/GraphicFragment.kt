package ua.kpi.comsys.IP8415.Fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import ua.kpi.comsys.IP8415.R
import ua.kpi.comsys.IP8415.dpToPx
import ua.kpi.comsys.IP8415.ui.CubicFunction

class GraphicFragment : Fragment(R.layout.fragment_graphics) {
    private class DrawView(context: Context?) : View(context) {
        var startX = 0f
        var startY = 0f
        var endX = 0f
        var endY = 0f
        var avgX = 0f
        var avgY = 0f

        val startPoint = -3f
        val endPoint = 3f
        val pointsQuantity = 100
        val graphic = CubicFunction()

        val paint: Paint = Paint()
        val arrowSize = dpToPx(7f, this.context)
        val serifSize = dpToPx(12f, this.context)

        init {
            paint.color = Color.GREEN
            paint.strokeWidth = dpToPx(2f, this.context)
        }

        private fun drawAxes(canvas: Canvas) {
            // axes
            canvas.drawLine(startX, avgY, endX, avgY, paint)
            canvas.drawLine(avgX, startY, avgX, endY, paint)
            // axes arrows
            canvas.drawLine(endX, avgY, endX - arrowSize, avgY - arrowSize, paint)
            canvas.drawLine(endX, avgY, endX - arrowSize, avgY + arrowSize, paint)
            canvas.drawLine(avgX, startY, avgX + arrowSize, startY + arrowSize, paint)
            canvas.drawLine(avgX, startY, avgX - arrowSize, startY + arrowSize, paint)
        }

        private fun drawGraphic(canvas: Canvas) {
            val points = graphic.getPoints(startPoint, endPoint, pointsQuantity)
            val scale = (endX - startX) / (endPoint - startPoint)
//            val scaleY = (endY - startY) / (points.last()[1] - points[0][1])

            points[0][0] = points[0][0] * scale + avgX
            points[0][1] = points[0][1] * -scale + avgY
            var previousPoint = points[0]

            for (point in points.slice(1 until points.size)) {
                point[0] = point[0] * scale + avgX
                point[1] = point[1] * -scale + avgY
                canvas.drawLine(previousPoint[0], previousPoint[1], point[0], point[1], paint)
                previousPoint = point
            }
            canvas.drawLine(avgX + scale, avgY + serifSize / 2,
                avgX + scale, avgY - serifSize / 2, paint)
            canvas.drawLine(avgX - serifSize / 2, avgY - scale,
                avgX + serifSize / 2, avgY - scale, paint)
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            startX = translationX
            startY = translationY
            endX = startX + width
            endY = startY + height
            avgY = (startY + endY) / 2
            avgX = (startX + endX) / 2

            Log.d("TAG", startX.toString())
            Log.d("TAG", startY.toString())
            Log.d("TAG", endX.toString())
            Log.d("TAG", endY.toString())

            drawAxes(canvas)
            drawGraphic(canvas)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_graphics, container, false) as ConstraintLayout
        val view = DrawView(this.context)
        root.addView(view)
        return root
    }
}