package com.example.dogbreeds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
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
    private lateinit var spinner: Spinner
    private var dogsListImage = mutableListOf<String>()
    private var breedsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner=findViewById(R.id.spinner)

        recyclerView = findViewById(R.id.recycler_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DogsAdapter(dogsListImage)
        recyclerView.adapter = adapter
    }

    private fun getListImages() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getImagesByBreed("breed/hound/images")
            val response = call.body()

            runOnUiThread{
                if (call.isSuccessful) {
                    val images = response?.images ?: emptyList()
                    dogsListImage.clear()
                    dogsListImage.addAll(images)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getListBreeds() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getBreeds("breed/list/all")
            val response = call.body()

            runOnUiThread{
                if (call.isSuccessful) {
                    val breedsMap = response?.message
                    if(breedsMap!=null){
                        for (breed in breedsMap.keys){
                            breedsList.add(breed)
                        }
                        setSpinner()
                    }
                }
            }
        }
    }




    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_DOGS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val URL_DOGS = "https://dog.ceo/api/"
    }
}