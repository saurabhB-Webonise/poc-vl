package com.example.pocvirginialottery

data class PlacesModel(
    val html_attributions: List<Any>,
    val next_page_token:String,
    val results: List<Result>,
    val status: String
)

data class Result(
    val business_status: String,
    val formatted_address: String,
    val geometry: Geometry,
    val icon: String,
    val name: String,
    val opening_hours: OpeningHours,
    val photos: List<Photo>,
    val place_id: String,
    val plus_code: PlusCode,
    val rating: Double,
    val reference: String,
    val types: List<String>,
    val user_ratings_total: Int
)

data class Geometry(
    val location: Location,
    val viewport: Viewport
)

data class OpeningHours(
    val open_now: Boolean
)

data class Photo(
    val height: Int,
    val html_attributions: List<String>,
    val photo_reference: String,
    val width: Int
)

data class PlusCode(
    val compound_code: String,
    val global_code: String
)

data class Location(
    val lat: Double,
    val lng: Double
)

data class Viewport(
    val northeast: Northeast,
    val southwest: Southwest
)

data class Northeast(
    val lat: Double,
    val lng: Double
)

data class Southwest(
    val lat: Double,
    val lng: Double
)