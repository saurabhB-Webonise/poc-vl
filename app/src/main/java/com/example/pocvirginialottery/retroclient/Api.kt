package com.example.pocvirginialottery.retroclient

import com.example.pocvirginialottery.PlacesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/maps/api/place/textsearch/json")
    fun getPlaces(
        @Query("query") query: String,
        @Query("key") key: String
    ):Call<PlacesModel>
}