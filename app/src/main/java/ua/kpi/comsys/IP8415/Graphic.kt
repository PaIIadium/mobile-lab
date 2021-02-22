package ua.kpi.comsys.IP8415.ui

import kotlin.math.pow

interface Graphic {
    fun getPoints(startX : Float, endX : Float, quantity: Int) : Array<FloatArray>
}

class CubicFunction : Graphic {
    override fun getPoints(startX: Float, endX: Float, quantity: Int): Array<FloatArray> {
        val result = Array(quantity, fun (_: Int) : FloatArray { return FloatArray(2) })
        val step = (endX - startX) / (quantity - 1)
        var currentValue = startX
        result[0][0] = currentValue
        result[0][1] = currentValue.pow(3)
        for (i in 1 until quantity) {
            currentValue += step
            result[i][0] = currentValue
            result[i][1] = currentValue.pow(3)
        }
        return result
    }

}