package ua.kpi.comsys.IP8415

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

class BooksLoader(val name: String, db : Database, cb: (List<Book>) -> Unit) {
    init {
        val contentType = MediaType.get("application/json")
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder().baseUrl("https://api.itbook.store/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        val apiService = retrofit.create(APIService::class.java)

        GlobalScope.launch {
            try {
                val response = apiService.getBooks(name)
                val books = response.books
                val bookEntities = createBookEntities(books)
                for (book in bookEntities) {
                    db.getDao().insertBook(book)
                }

                withContext(Dispatchers.Main) {
                    cb(books)
                }
            } catch (err: Error) {
                val bookEntities = db.getDao().getBooksByUrl(name)
                if (bookEntities.isNotEmpty()) {
                    val books = createBooks(bookEntities)
                    withContext(Dispatchers.Main) {
                        cb(books)
                    }
                } else {
                    cb(listOf())
                }
            }
        }
    }

    fun createBookEntities(books: List<Book>) : List<BookEntity> {
        val bookEntities = mutableListOf<BookEntity>()
        for (book in books) {
            val bookEntity = BookEntity(
                book.title,
                book.subtitle,
                book.isbn13,
                book.price,
                book.image,
            )
            bookEntities.add(bookEntity)
        }
        return bookEntities
    }

    fun createBooks(bookEntities: List<BookEntity>) : List<Book> {
        val books = mutableListOf<Book>()

        for (book in bookEntities) {
            val book = Book(
                book.title,
                book.subtitle,
                book.isbn13,
                book.price,
                book.image,
            )
            books.add(book)
        }
        return books
    }

}