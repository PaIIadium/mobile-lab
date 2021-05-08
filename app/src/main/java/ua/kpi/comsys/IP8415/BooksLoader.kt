package ua.kpi.comsys.IP8415

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

class BooksLoader(name: String, cb: (ArrayList<Book>) -> Unit) {
    init {
        val contentType = MediaType.get("application/json")
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder().baseUrl("https://api.itbook.store/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        val apiService = retrofit.create(APIService::class.java)

        GlobalScope.launch {
            val response = apiService.getBooks(name)
            val books = response.books
            withContext(Dispatchers.Main) {
                cb(books)
            }
        }
    }
}