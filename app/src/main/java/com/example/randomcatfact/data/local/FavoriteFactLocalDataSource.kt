package com.example.randomcatfact.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteFactLocalDataSource @Inject constructor(
    private val dao: FavoriteFactDao
) {
    suspend fun saveFavorite(fact: String) {
        dao.saveFavorite(FavoriteFact(fact = fact))
    }
    suspend fun removeFavorite(fact:String){
        dao.removeFavorite(fact)
    }

    fun getFavorites(): Flow<List<FavoriteFact>> {
        return dao.getFavorites()
    }
}