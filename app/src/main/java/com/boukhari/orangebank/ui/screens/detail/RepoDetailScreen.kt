package com.boukhari.orangebank.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.boukhari.orangebank.R
import com.boukhari.orangebank.ui.screens.list.ErrorText
import com.boukhari.orangebank.ui.screens.shared.LoadingAnimation
import com.boukhari.orangebank.ui.screens.shared.OrangeBankTopAppBar


@Composable
fun RepoDetailScreen(viewModel: RepoDetailViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = { OrangeBankTopAppBar(title = stringResource(R.string.repositories)) },
    ) {

        val data = state.value
        when {
            data.repoDetail != null -> {
                val repo = data.repoDetail
                Column {
                    Headline(text = repo.fullName)
                    Spacer(modifier = Modifier.height(24.dp))
                    Description(text = repo.description)
                }
            }
            state.value.isLoading -> {
                LoadingAnimation()
            }
            state.value.error.isNotEmpty() -> {
                ErrorText(error = state.value.error)
            }
        }
    }
}


@Composable
fun Headline(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
fun Description(text: String?) {
    Text(
        text = text ?: stringResource(R.string.description_parsing_error),
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Justify,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

