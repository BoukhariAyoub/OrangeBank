package com.boukhari.orangebank.ui.screens.detail

import com.boukhari.orangebank.domain.model.Repo

data class RepoDetailState(
    val isLoading: Boolean = false,
    val repoDetail: Repo? = null,
    val error: String = ""
)
