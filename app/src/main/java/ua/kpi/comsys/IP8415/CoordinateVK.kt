package ua.kpi.comsys.IP8415

import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sign

class CoordinateVK(val degrees: Int, val minutes: UInt, var seconds: UInt, val direction: Direction) {

 init {
     if (degrees < -90 && degrees > 90
            && minutes < 0u && minutes >= 60u
            && seconds < 0u && seconds >= 60u) {
            throw Error("Wrong coordinate")
        }
 }
    constructor() : this(0, 0u, 0u, Direction.longitude)

    fun getLetter() : Char {
        return if (direction == Direction.latitude && degrees >= 0) 'N'
        else if (direction == Direction.latitude) 'S'
        else if (degrees >= 0) 'E'
        else 'W'
    }

    fun getCanonicalString() : String {
        return "${degrees.absoluteValue}°$minutes′$seconds″ ${getLetter()}"
    }

    fun getDecimalString() : String {
        return "${degrees.absoluteValue},${((minutes * 60u + seconds).toFloat() / 0.036).roundToInt()}° ${getLetter()}"
    }

    fun getAverageCoordinate(coordinate : CoordinateVK) : CoordinateVK? {
        if (this.direction != coordinate.direction) return null
        val sign1 = degrees.sign
        val sign2 = coordinate.degrees.sign
        val sum = (degrees * 3600) + minutes.toInt() * 60 * sign1 + seconds.toInt() * sign1 +
                coordinate.degrees * 3600 + coordinate.minutes.toInt() * 60 * sign2 + coordinate.seconds.toInt() * sign2
        val halfSeconds = sum / 2
        val newDegrees = halfSeconds / 3600
        val leftSeconds = (halfSeconds - newDegrees * 3600).absoluteValue
        val newMinutes = leftSeconds / 60
        val newSeconds = leftSeconds - newMinutes * 60
        return CoordinateVK(newDegrees, newMinutes.toUInt(), newSeconds.toUInt(), direction)
    }

    companion object {
        fun getAverageCoordinate(coordinate1: CoordinateVK, coordinate2: CoordinateVK) : CoordinateVK? {
            return coordinate1.getAverageCoordinate(coordinate2)
        }
    }
}
