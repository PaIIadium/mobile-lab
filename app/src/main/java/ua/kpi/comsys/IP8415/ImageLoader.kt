package ua.kpi.comsys.IP8415

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ImageLoader {
    fun getImage(url: String, cb: (Bitmap) -> Unit) {
        val retrofit = Retrofit.Builder().baseUrl("https://localhost")
            .build()
        val apiService = retrofit.create(APIService::class.java)
        GlobalScope.launch {
            val response = apiService.getBookImage(url)
            val bytes = response.bytes()
            val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            withContext(Dispatchers.Main) {
                cb(image)
            }
        }
    }
}