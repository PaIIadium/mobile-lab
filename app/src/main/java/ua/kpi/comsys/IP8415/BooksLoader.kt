package ua.kpi.comsys.IP8415

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BooksLoader(jsonPath : String, ctx: Context) {
    private val books : BooksJSON
    init {
        val stream = ctx.assets.open(jsonPath)
        val jsonString = stream.bufferedReader().readText()
        books = Json.decodeFromString(jsonString)
    }

    fun getBooks() : ArrayList<Book> {
        return books.books
    }
}