package ua.kpi.comsys.IP8415

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

class ImagesURLLoader(name: String, count: Int, cb: (ArrayList<ImageURL>) -> Unit) {
    init {
        val contentType = MediaType.get("application/json")
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder().baseUrl("https://pixabay.com/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        val apiService = retrofit.create(APIService::class.java)

        GlobalScope.launch {
            val response = apiService.getImagesURL(name, count)
            Log.d("TAG", response.toString())
            val imagesURL = response.hits
            cb(imagesURL)
        }
    }
}