package com.example.dogbreeds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DogsAdapter
    private var dogsListImage = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DogsAdapter(dogsListImage)
        recyclerView.adapter = adapter
        getListImages()
    }

    private fun getListImages() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getImagesByBreed("breed/hound/images")
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val images = response?.images ?: emptyList()
                    dogsListImage.addAll(images)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}