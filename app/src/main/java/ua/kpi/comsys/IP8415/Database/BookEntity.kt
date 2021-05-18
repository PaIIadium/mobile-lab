package ua.kpi.comsys.IP8415.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    val title: String,
    val subtitle: String,
    @PrimaryKey val isbn13: String,
    val price: String,
    val image: String
    )