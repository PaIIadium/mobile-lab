package ua.kpi.comsys.IP8415.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Query("Select * from BookEntity where title like :name")
    fun getBooksByUrl(name: String) : List<BookEntity>

    @Insert
    fun insertBook(book: BookEntity)

    @Update
    fun updateBook(book: BookEntity)
}