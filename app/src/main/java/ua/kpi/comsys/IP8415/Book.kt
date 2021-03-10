package ua.kpi.comsys.IP8415
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.serialization.Serializable

@Serializable
class Book(val title : String, val subtitle: String, val isbn13: String, val price: String, private val image: String) {
    fun getImageBitmap(ctx : Context) : Bitmap? {
        if (image != "") return BitmapFactory.decodeStream(ctx.assets.open("Images/$image"))
        return null
    }
}