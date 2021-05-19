package ua.kpi.comsys.IP8415

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import java.io.IOException

class ExtendedBookLoader(isbn13: String, cb: (ExtendedBookServerResponse) -> Unit) {
    private var extendedBook : ExtendedBook? = try {
        val contentType = MediaType.get("application/json")
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder().baseUrl("https://api.itbook.store/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        val apiService = retrofit.create(APIService::class.java)

        GlobalScope.launch {
            try {
                val response = apiService.getExtendedBook(isbn13)
                Log.d("TAG", response.toString())
                withContext(Dispatchers.Main) {
                    cb(response)
                }
            }
            catch (err: Exception) {

            }
        }
        null
    } catch (err: IOException) {
        null
    }
}