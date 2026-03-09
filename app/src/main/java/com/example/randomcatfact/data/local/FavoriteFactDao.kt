package com.example.randomcatfact.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy

@Dao
interface FavoriteFactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveFavorite(fact: FavoriteFact)

    @Query("DELETE FROM favorite_facts WHERE fact= :fact")
    suspend fun removeFavorite(fact: String)

    @Query("SELECT * FROM favorite_facts ORDER BY id DESC")
    fun getFavorites(): Flow<List<FavoriteFact>>
}