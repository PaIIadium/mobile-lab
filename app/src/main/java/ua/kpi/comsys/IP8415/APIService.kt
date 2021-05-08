package ua.kpi.comsys.IP8415

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface APIService {
    @GET("1.0/search/{query}")
    suspend fun getBooks(@Path("query") query: String): BooksServerResponse
    @GET
    suspend fun getBookImage(@Url url: String): ResponseBody
    @GET("1.0/books/{isbn13}")
    suspend fun getExtendedBook(@Path("isbn13") isbn13: String): ExtendedBookServerResponse
    @GET("api/?key=19193969-87191e5db266905fe8936d565&image_type=photo")
    suspend fun getImagesURL(@Query("q") query: String, @Query("per_page") count: Int): ImagesURLServerResponse
}