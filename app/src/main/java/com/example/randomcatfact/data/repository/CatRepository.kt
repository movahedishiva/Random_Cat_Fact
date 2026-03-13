package com.example.randomcatfact.data.repository

import com.example.randomcatfact.data.local.FavoriteFact
import com.example.randomcatfact.util.Result
import kotlinx.coroutines.flow.Flow

interface CatRepository {

    suspend fun getFact(): Result<String>
    suspend fun saveFavorite(fact: String)
    suspend fun removeFavorite(fact:String)
    fun getFavorites(): Flow<List<FavoriteFact>>
}