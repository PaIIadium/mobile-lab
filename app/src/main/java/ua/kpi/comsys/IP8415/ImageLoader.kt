package ua.kpi.comsys.IP8415

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream

class ImageLoader {
    fun getImage(url: String, ctx: Context, cb: (Bitmap) -> Unit) {
        val retrofit = Retrofit.Builder().baseUrl("https://localhost")
            .build()
        val apiService = retrofit.create(APIService::class.java)
        GlobalScope.launch {
            try {
                val image = readImage(ctx, url)
                withContext(Dispatchers.Main) {
                    cb(image)
                }
            }
            catch (err: Exception) {
                try {
                    val response = apiService.getBookImage(url)
                    val bytes = response.bytes()
                    val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    writeImage(ctx, image, url)
                    withContext(Dispatchers.Main) {
                        cb(image)
                    }
                } catch (err: Exception) {

                }
            }
        }
    }

    private fun writeImage(ctx: Context, image: Bitmap, url: String) {
        val wrapper = ContextWrapper(ctx)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "${url.hashCode()}.png")
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()
    }

    private fun readImage(ctx: Context, url: String): Bitmap {
        val wrapper = ContextWrapper(ctx)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "${url.hashCode()}.png")
        val bytes = file.readBytes()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

}