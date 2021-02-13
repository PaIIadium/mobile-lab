package ua.kpi.comsys.IP8415

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val coord1 = CoordinateVK(-15, 14u, 13u, Direction.latitude)
        Log.d("Tag", "Coordinate 1: ${coord1.getCanonicalString()}")
        val coord2 = CoordinateVK(20, 53u, 16u, Direction.latitude)
        Log.d("Tag", "Coordinate 2 (decimal): ${coord2.getDecimalString()}")
        val averageCoord = coord1.getAverageCoordinate(coord2)?.getCanonicalString()
        if (averageCoord != null) Log.d("Tag", "Average coordinate: $averageCoord")
        val averageCoord2 = CoordinateVK.getAverageCoordinate(coord1, coord2)?.getDecimalString()
        if (averageCoord2 != null) Log.d("Tag", "Average coordinate (static, decimal): $averageCoord2")
        val zeroCoord = CoordinateVK().getDecimalString()
        Log.d("Tag", "Zero coordinate (default initializer): $zeroCoord")
    }
}