package ua.kpi.comsys.IP8415

import kotlinx.serialization.Serializable

@Serializable
data class ImagesURLServerResponse (
    val hits: ArrayList<ImageURL>
)

@Serializable
data class ImageURL(
    val webformatURL: String
)

