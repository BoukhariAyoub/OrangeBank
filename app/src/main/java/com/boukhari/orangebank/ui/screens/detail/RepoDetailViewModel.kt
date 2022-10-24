package com.boukhari.orangebank.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boukhari.orangebank.domain.GetRepoUseCase
import com.boukhari.orangebank.domain.Resource
import com.boukhari.orangebank.ui.screens.navigation.REPO_DETAIL_ID_EXTRA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: GetRepoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RepoDetailState())
    val state: StateFlow<RepoDetailState> = _state

    init {
        savedStateHandle.get<Int>(REPO_DETAIL_ID_EXTRA)?.let { id ->
            getRepoById(id)
        }
    }


    private fun getRepoById(id: Int) {
        useCase.getRepoById(id).onEach { result ->
            when (result) {
                is Resource.Success -> _state.value = RepoDetailState(repoDetail = result.data)
                is Resource.Error -> _state.value =
                    RepoDetailState(error = result.message ?: "Unknown error")
                is Resource.Loading -> RepoDetailState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}