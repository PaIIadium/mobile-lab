package ua.kpi.comsys.IP8415.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Query("Select * from BookEntity where title like :name")
    fun getBooksByUrl(name: String) : List<BookEntity>

    @Query("Select * from BookEntity where isbn13 = :isbn13")
    fun getBookByIsbn13(isbn13: String) : List<BookEntity>

    @Insert
    fun insertBook(book: BookEntity)

    @Update
    fun updateBook(book: BookEntity)

    @Query("Select * from UrlEntity where url = :url")
    fun getImageUrl(url: String) : List<UrlEntity>

    @Query("Select * from UrlEntity")
    fun getImagesUrl() : List<UrlEntity>

    @Insert
    fun insertImageUrl(url: UrlEntity)

    @Update
    fun updateImageUrl(url: UrlEntity)

    @Query("Delete from UrlEntity")
    fun deleteImagesUrl()
}