package ua.kpi.comsys.IP8415

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.serialization.Serializable

@Serializable
class ExtendedBook(val title : String, val subtitle: String, val desc: String, val authors: String,
                   val isbn13: String, val pages: String, val year: String, val rating: String,
                   val price: String, val image: String, val publisher: String) {
    fun getImageBitmap(ctx : Context) : Bitmap? {
        if (image != "") return BitmapFactory.decodeStream(ctx.assets.open("Images/$image"))
        return null
    }
}