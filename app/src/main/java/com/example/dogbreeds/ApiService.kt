package com.example.dogbreeds

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiService {
    @GET
    suspend fun getImagesByBreed(@Url url: String): Response<DogResponse>
}