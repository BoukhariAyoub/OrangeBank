package com.boukhari.orangebank.ui.screens.list

import com.boukhari.orangebank.domain.model.Repo

data class RepoListState(
    val isLoading: Boolean = false,
    val repoList: List<Repo> = emptyList(),
    val error: String = ""
)
