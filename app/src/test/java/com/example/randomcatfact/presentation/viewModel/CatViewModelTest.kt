package com.example.randomcatfact.presentation.viewModel

import com.example.randomcatfact.MainDispatcherRule
import com.example.randomcatfact.data.local.FavoriteFact
import com.example.randomcatfact.data.repository.CatRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CatViewModel

    private lateinit var repository: CatRepository


    @Before
    fun setup() {
        repository = mockk()
        coEvery { repository.getFavorites() } returns flowOf(emptyList())
    }


    @Test
    fun `when getFact is successful, state should be Success`() = runTest {
        val expectedFact = "This is a fact"
        coEvery { repository.getFact() } returns expectedFact
        viewModel = CatViewModel(repository)
        assertEquals(CatFactState.Loading, viewModel.state.value)
        advanceUntilIdle()

        val currentState = viewModel.state.value
        assertEquals(CatFactState.Success(expectedFact), currentState)
    }

    @Test
    fun `when getFact throws exception, state should be Error`() = runTest {
        val errorMessage = "Network Error"

        coEvery { repository.getFact() } throws Exception(errorMessage)

        viewModel = CatViewModel(repository)
        assertEquals(CatFactState.Loading, viewModel.state.value)
        advanceUntilIdle()

        val currentState = viewModel.state.value
        assertEquals(CatFactState.Error(errorMessage), currentState)
    }

    @Test
    fun `when saving a NEW fact, repository saveFavorite SHOULD be called`() = runTest {

        val newFact = "This is a new fact"

        coEvery { repository.getFavorites() } returns flowOf(emptyList())
        coEvery { repository.saveFavorite(any()) } returns Unit

        viewModel = CatViewModel(repository)


        viewModel.saveFavorite(newFact)
        advanceUntilIdle()


        coVerify(exactly = 1) { repository.saveFavorite(newFact) }
    }

    @Test
    fun `when saving a DUPLICATE fact, repository saveFavorite should NOT be called`() = runTest {

        val duplicateFact = "This is a duplicate fact"
        val existingList = listOf(FavoriteFact(id = 1, fact = duplicateFact))

        coEvery { repository.getFavorites() } returns flowOf(existingList)
        coEvery { repository.saveFavorite(any()) } returns Unit

        viewModel = CatViewModel(repository)
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.favorites.collect {}
        }

        viewModel.saveFavorite(duplicateFact)


        advanceUntilIdle()


        coVerify(exactly = 0) { repository.saveFavorite(duplicateFact) }
        collectJob.cancel()
    }
}