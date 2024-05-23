package com.example.clase5_retrofit

import com.google.gson.annotations.SerializedName

data class BreedsResponse (
    val message: Map<String, List<String>>,
    val status: String
)