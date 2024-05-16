package com.example.dogbreeds

import com.google.gson.annotations.SerializedName


data class DogResponse (

    @SerializedName("message") var images: MutableList<String> = mutableListOf<String>(),
    @SerializedName("status") var status  : String? = null

)