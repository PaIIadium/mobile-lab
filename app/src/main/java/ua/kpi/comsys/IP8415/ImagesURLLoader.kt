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
import ua.kpi.comsys.IP8415.Database.BookEntity
import ua.kpi.comsys.IP8415.Database.Database
import ua.kpi.comsys.IP8415.Database.UrlEntity

class ImagesURLLoader(name: String, count: Int, val db: Database, cb: (List<ImageURL>) -> Unit) {
    init {
        val contentType = MediaType.get("application/json")
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder().baseUrl("https://pixabay.com/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        val apiService = retrofit.create(APIService::class.java)

        GlobalScope.launch {
            val urlEntities = db.getDao().getImagesUrl()
            if (urlEntities.isNotEmpty()) {
                val urls = createImageUrls(urlEntities)
                withContext(Dispatchers.Main) {
                    cb(urls)
                }
            } else {
                try {
                    val response = apiService.getImagesURL(name, count)
                    val imagesURL = response.hits
                    val imagesURLEntities = createImagesUrlEntities(imagesURL)
                    for (imageURL in imagesURLEntities) {
                        val existingImageURL = db.getDao().getImageUrl(imageURL.url)
                        if (existingImageURL.isEmpty()) db.getDao().insertImageUrl(UrlEntity(imageURL.url))
                    }
                    cb(imagesURL)
                } catch (err: Exception) {

                }
            }
        }
    }

    private fun createImagesUrlEntities(imagesURL: ArrayList<ImageURL>): List<UrlEntity> {
        val urlEntities = mutableListOf<UrlEntity>()
        for (url in imagesURL) {
            val urlEntity = UrlEntity(url.webformatURL)
            urlEntities.add(urlEntity)
        }
        return urlEntities
    }

    private fun createImageUrls(urlEntities: List<UrlEntity>): List<ImageURL> {
        val urls = mutableListOf<ImageURL>()

        for (urlEntity in urlEntities) {
            val url = ImageURL(urlEntity.url)
            urls.add(url)
        }
        return urls
    }
}