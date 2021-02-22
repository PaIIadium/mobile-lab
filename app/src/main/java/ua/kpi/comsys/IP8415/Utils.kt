package ua.kpi.comsys.IP8415

import android.content.Context
import android.util.TypedValue


fun dpToPx(dp: Float, context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )
}