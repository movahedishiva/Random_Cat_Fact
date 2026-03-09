package com.example.randomcatfact.data.repository

import com.example.randomcatfact.data.local.FavoriteFact
import kotlinx.coroutines.flow.Flow

interface CatRepository {

    suspend fun getFact():String
    suspend fun saveFavorite(fact: String)
    suspend fun removeFavorite(fact:String)
    fun getFavorites(): Flow<List<FavoriteFact>>
}