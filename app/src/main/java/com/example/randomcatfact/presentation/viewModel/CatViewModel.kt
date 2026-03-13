package com.example.randomcatfact.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomcatfact.data.repository.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.any

import com.example.randomcatfact.util.Result

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {

    private val _state =
        MutableStateFlow<CatFactState>(CatFactState.Loading)

    val state = _state.asStateFlow()

    val favorites =
        repository.getFavorites().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        getFact()
    }

    fun getFact() {

        viewModelScope.launch {

            _state.value = CatFactState.Loading


                val result = repository.getFact()

             when(result){
                 is Result.Success -> _state.update {
                     CatFactState.Success(result.data)
                 }

                 is Result.Error -> _state.update {
                     CatFactState.Error(result.exception.message.toString())
                 }
             }


        }
    }

    fun saveFavorite(fact: String) {

        viewModelScope.launch {
            if(!favorites.value.any{it.fact==fact})
            repository.saveFavorite(fact)

        }
    }

    fun removeFavorite(fact: String) {

        viewModelScope.launch {


            repository.removeFavorite(fact)

        }
    }
}

sealed class CatFactState {

    data object Loading : CatFactState()

    data class Success(
        val fact: String
    ) : CatFactState()

    data class Error(
        val message: String
    ) : CatFactState()
}