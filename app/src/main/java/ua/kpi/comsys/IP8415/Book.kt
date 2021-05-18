package ua.kpi.comsys.IP8415
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import retrofit2.Retrofit

@Serializable
class Book(val title : String, val subtitle: String, val isbn13: String, val price: String, val image: String) {
    @Transient
    var bookImage: Bitmap? = null
    fun getImageBitmap(viewHolder: BookViewHolder) {
        if (bookImage != null) {
            viewHolder.bookImage.setImageBitmap(bookImage)
            return
        }
        val retrofit = Retrofit.Builder().baseUrl("https://localhost")
            .build()
        val apiService = retrofit.create(APIService::class.java)
        GlobalScope.launch {
            val response = apiService.getBookImage(image)
            val bytes = response.bytes()
            val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            bookImage = image
            withContext(Dispatchers.Main) {
                viewHolder.bookImage.setImageBitmap(image)
            }
        }
    }
}