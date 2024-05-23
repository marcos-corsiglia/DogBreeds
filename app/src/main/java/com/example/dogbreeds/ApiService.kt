package com.example.dogbreeds

import com.example.clase5_retrofit.BreedsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiService {
    @GET
    suspend fun getImagesByBreed(@Url url: String): Response<DogResponse>
    suspend fun getBreeds(@Url url: String): Response<BreedsResponse>
}