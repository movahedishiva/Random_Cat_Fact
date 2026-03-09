package com.example.randomcatfact.data.remote

import javax.inject.Inject

class CatRemoteDataSource @Inject constructor(
    private val api: CatFactApi
) {
    suspend fun getFact(): String {
        return api.getRandomFact().fact
    }
}