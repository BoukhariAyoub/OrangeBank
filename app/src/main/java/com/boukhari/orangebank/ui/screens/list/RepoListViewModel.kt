package com.boukhari.orangebank.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boukhari.orangebank.domain.GetRepoUseCase
import com.boukhari.orangebank.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val useCase: GetRepoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RepoListState())
    val state: StateFlow<RepoListState> = _state

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh: StateFlow<Boolean> = _isRefresh

    private val _showToast = MutableStateFlow("")
    val showToast: StateFlow<String> = _showToast

    init {
        getRepos()
    }

    private fun getRepos() {
        useCase.getRepos().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("RepoListViewModel", "Got data : ${result.data}")
                    _state.emit(RepoListState(repoList = result.data ?: emptyList()))
                }
                is Resource.Error -> {
                    Log.e("RepoListViewModel", result.message ?: "Unknown error")

                    if (_state.value.repoList.isNotEmpty()) {
                        //keep the current result  and show error as toast
                        _showToast.emit(result.message ?: "Unknown error")
                        _state.emit(RepoListState(repoList = _state.value.repoList))
                    } else {
                        _state.emit(
                            RepoListState(error = result.message ?: "Unknown error")
                        )
                    }
                }
                is Resource.Loading -> {
                    if (_state.value.repoList.isEmpty()) {
                        //only show loading when data is empty
                        _state.emit(RepoListState(isLoading = true))
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefresh.emit(true)
            getRepos()
            _isRefresh.emit(false)
        }
    }

    fun onToastShown() {
        viewModelScope.launch {
            _showToast.emit("")
        }
    }

}
