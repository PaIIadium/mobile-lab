package ua.kpi.comsys.IP8415.Fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import ua.kpi.comsys.IP8415.R
import ua.kpi.comsys.IP8415.dpToPx

class DiagramFragment : Fragment(R.layout.fragment_graphics) {
    private class DrawView(context: Context?) : View(context) {
        var startX = 0f
        var startY = 0f
        var endX = 0f
        var endY = 0f
        var avgX = 0f
        var avgY = 0f
        var bigRadius = 0f
        var smallRadius = 0f

        val yellowFraction = 0.15
        val brownFraction = 0.25
        val grayFraction = 0.45
        val redFraction = 0.1
        val purpleFraction = 0.05

        val paintGraphic: Paint = Paint()
        val paintBackground = Paint()
        val paintYellow = Paint()
        val paintBrown = Paint()
        val paintGray = Paint()
        val paintRed = Paint()
        val paintPurple = Paint()

        init {
            paintGraphic.color = Color.GREEN
            paintGraphic.strokeWidth = dpToPx(2f, this.context)

            paintYellow.color = Color.YELLOW
            paintBrown.color = Color.rgb(101, 67, 33)
            paintGray.color = Color.GRAY
            paintRed.color = Color.RED
            paintPurple.color = Color.rgb(139, 0, 255)
        }

        private fun drawDiagram(canvas: Canvas) {
            val yellowSweepAngle = (360f * yellowFraction).toFloat()
            val brownSweepAngle = (360f * brownFraction).toFloat()
            val graySweepAngle = (360f * grayFraction).toFloat()
            val redSweepAngle = (360f * redFraction).toFloat()
            val purpleSweepAngle = (360f * purpleFraction).toFloat()

            val yellowStartAngle = 0f
            val brownStartAngle = yellowSweepAngle
            val grayStartAngle = yellowSweepAngle + brownSweepAngle
            val redStartAngle = yellowSweepAngle + brownSweepAngle + graySweepAngle
            val purpleStartAngle = yellowSweepAngle + brownSweepAngle + graySweepAngle + redSweepAngle

            canvas.drawArc(avgX - bigRadius, avgY - bigRadius, avgX + bigRadius, avgY + bigRadius, yellowStartAngle, yellowSweepAngle, true, paintYellow)
            canvas.drawArc(avgX - bigRadius, avgY - bigRadius, avgX + bigRadius, avgY + bigRadius, brownStartAngle, brownSweepAngle, true, paintBrown)
            canvas.drawArc(avgX - bigRadius, avgY - bigRadius, avgX + bigRadius, avgY + bigRadius, grayStartAngle, graySweepAngle, true, paintGray)
            canvas.drawArc(avgX - bigRadius, avgY - bigRadius, avgX + bigRadius, avgY + bigRadius, redStartAngle, redSweepAngle, true, paintRed)
            canvas.drawArc(avgX - bigRadius, avgY - bigRadius, avgX + bigRadius, avgY + bigRadius, purpleStartAngle, purpleSweepAngle, true, paintPurple)
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            paintBackground.color = (rootView.background as ColorDrawable).color
            startX = x
            startY = y
            endX = startX + width
            endY = startY + height
            avgY = (startY + endY) / 2
            avgX = (startX + endX) / 2

            bigRadius =  if (width < height) width / 2f else height / 2f
            smallRadius = bigRadius - bigRadius / 2f

            drawDiagram(canvas)
            canvas.drawArc(avgX - smallRadius, avgY - smallRadius, avgX + smallRadius, avgY + smallRadius, 0f, 360f, true, paintBackground)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_diagram, container, false) as ConstraintLayout
        val diagramView = root.findViewById<FrameLayout>(R.id.diagram)
        val view = DrawView(this.context)
        diagramView.addView(view)
        return root
    }
}