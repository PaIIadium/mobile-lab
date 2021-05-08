package ua.kpi.comsys.IP8415

import kotlinx.serialization.Serializable

@Serializable
data class ExtendedBookServerResponse (
    val title: String,
    val subtitle: String,
    val desc: String,
    val authors: String,
    val isbn13: String,
    val pages: String,
    val year: String,
    val rating: String,
    val price: String,
    val image: String,
    val publisher: String
)