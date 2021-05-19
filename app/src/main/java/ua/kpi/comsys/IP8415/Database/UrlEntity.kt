package ua.kpi.comsys.IP8415.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UrlEntity(
    @PrimaryKey val url: String
)