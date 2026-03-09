package com.example.randomcatfact.data.remote
import com.example.randomcatfact.data.model.CatFactResponse
import retrofit2.http.GET

interface CatFactApi {

    @GET("fact")
    suspend fun getRandomFact(): CatFactResponse
}