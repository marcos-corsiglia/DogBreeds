package com.example.dogbreeds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
        getListBreeds()
    }

    private fun getListImages(breed: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getImagesByBreed("breed/$breed/images")
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
            val call = getRetrofit().create(ApiService::class.java).getBreeds("breeds/list/all")
            val response = call.body()

            runOnUiThread{  //AL terminar actualizá la interfaz de usuario
                if (call.isSuccessful) {
                    val breedsMap = response?.breeds
                    if(breedsMap!=null){
                        breedsList=breedsMap.keys.toMutableList()
                        //for (breed in breedsMap.keys){
                        //    breedsList.add(breed)
                        //}
                        //for (breed in breedsMap.keys){
                          //  breedsList.addAll(breedsMap[breed]!!)
                        //}
                        setSpinner()
                    }
                }
            }
        }
    }


    private fun setSpinner(){
        val spinnerAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,breedsList)
        spinner.adapter=spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getListImages(breedsList[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
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