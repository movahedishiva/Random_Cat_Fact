package com.example.randomcatfact.data.repository

import com.example.randomcatfact.data.local.FavoriteFact
import com.example.randomcatfact.data.local.FavoriteFactLocalDataSource
import com.example.randomcatfact.data.remote.CatRemoteDataSource
import com.example.randomcatfact.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext


class CatRepositoryImpl @Inject constructor(
    private val remote: CatRemoteDataSource,
    private val local: FavoriteFactLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CatRepository {

    override suspend fun getFact(): String {

     return withContext(ioDispatcher)
    { remote.getFact() }
}



    override suspend fun saveFavorite(fact: String) {
        withContext(ioDispatcher){
            local.saveFavorite(fact)
        }

    }

    override suspend fun removeFavorite(fact: String) {
        withContext(ioDispatcher) {
            local.removeFavorite(fact)
        }
    }

    override fun getFavorites(): Flow<List<FavoriteFact>> {
        // Ensure the database flow collection happens on the IO thread
        return local.getFavorites().flowOn(ioDispatcher)
    }
}