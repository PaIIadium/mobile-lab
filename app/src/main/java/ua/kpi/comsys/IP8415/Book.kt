package ua.kpi.comsys.IP8415
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream


@Serializable
class Book(val title : String, val subtitle: String, val isbn13: String, val price: String, val image: String) {
    @Transient
    var bookImage: Bitmap? = null
    fun getImageBitmap(viewHolder: BookViewHolder, ctx: Context) {
        if (bookImage != null) {
            viewHolder.bookImage.setImageBitmap(bookImage)
            return
        }
        val retrofit = Retrofit.Builder().baseUrl("https://localhost")
            .build()
        val apiService = retrofit.create(APIService::class.java)
        GlobalScope.launch {
            try {
                val response = apiService.getBookImage(image)
                val bytes = response.bytes()
                val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                bookImage = image
                writeImage(ctx, image)
                withContext(Dispatchers.Main) {
                    viewHolder.bookImage.setImageBitmap(image)
                }
            } catch (err: Exception) {
                val image = readImage(ctx, isbn13)
                withContext(Dispatchers.Main) {
                    viewHolder.bookImage.setImageBitmap(image)
                }
            }
        }
    }

    private fun writeImage(ctx: Context, image: Bitmap) {
        val wrapper = ContextWrapper(ctx)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "${isbn13}.png")
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()
    }

    private fun readImage(ctx: Context, isbn13: String): Bitmap {
        val wrapper = ContextWrapper(ctx)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "${isbn13}.png")
        val bytes = file.readBytes()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}