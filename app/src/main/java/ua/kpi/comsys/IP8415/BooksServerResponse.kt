package ua.kpi.comsys.IP8415

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class BooksServerResponse (
    val error: String,
    val total: String,
    val page: String,
    val books: ArrayList<Book>
)
