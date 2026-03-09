package com.example.randomcatfact.data.repository


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.randomcatfact.data.local.AppDatabase
import com.example.randomcatfact.data.local.FavoriteFactDao
import com.example.randomcatfact.data.local.FavoriteFactLocalDataSource
import com.example.randomcatfact.data.remote.CatRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatRepositoryIntegrationTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: FavoriteFactDao
    private lateinit var remoteDataSource: CatRemoteDataSource
    private lateinit var localDataSource: FavoriteFactLocalDataSource
    private lateinit var repository: CatRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.favoriteFactDao()

        localDataSource = FavoriteFactLocalDataSource(dao)
        remoteDataSource = mockk()

        repository = CatRepositoryImpl(
            remote = remoteDataSource,
            local = localDataSource,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getFact_shouldReturnFactFromRemoteDataSource() = runTest(testDispatcher) {
        val expectedFact = "This is a fact"
        coEvery { remoteDataSource.getFact() } returns expectedFact

        val result = repository.getFact()

        assertEquals(expectedFact, result)
        coVerify(exactly = 1) { remoteDataSource.getFact() }
    }

    @Test
    fun saveFavorite_shouldInsertFactIntoRealDatabase() = runTest(testDispatcher) {
        val newFact = "This is a new fact"

        repository.saveFavorite(newFact)

        val favoritesList = repository.getFavorites().first()
        assertEquals(1, favoritesList.size)
        assertEquals(newFact, favoritesList[0].fact)
    }

    @Test
    fun removeFavorite_shouldDeleteFactFromRealDatabase() = runTest(testDispatcher) {
        val factToKeep = "This is a fact to keep"
        val factToRemove = "This is a fact to remove"

        repository.saveFavorite(factToKeep)
        repository.saveFavorite(factToRemove)

        repository.removeFavorite(factToRemove)

        val favoritesList = repository.getFavorites().first()
        assertEquals(1, favoritesList.size)
        assertEquals(factToKeep, favoritesList[0].fact)
    }

    @Test
    fun getFavorites_shouldReturnFlowOfFactsFromRealDatabase() = runTest(testDispatcher) {
        val fact1 = "Fact 1"
        val fact2 = "Fact 2"

        repository.saveFavorite(fact1)
        repository.saveFavorite(fact2)

        val favoritesList = repository.getFavorites().first()

        assertEquals(2, favoritesList.size)
        assertTrue(favoritesList.any { it.fact == fact1 })
        assertTrue(favoritesList.any { it.fact == fact2 })
    }
}