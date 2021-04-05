package ua.kpi.comsys.IP8415

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException

class ExtendedBookLoader(isbn13: String, ctx: Context) {
    private var extendedBook : ExtendedBook? = try {
        val stream = ctx.assets.open("Books/$isbn13.txt")
        val jsonString = stream.bufferedReader().readText()
        Json.decodeFromString(jsonString)
    } catch (err: IOException) {
        null
    }

    fun getBook() : ExtendedBook? {
        return extendedBook
    }
}