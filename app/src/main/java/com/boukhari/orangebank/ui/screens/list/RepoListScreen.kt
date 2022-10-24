package com.boukhari.orangebank.ui.screens.list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.boukhari.orangebank.R
import com.boukhari.orangebank.domain.model.Repo
import com.boukhari.orangebank.ui.screens.shared.LoadingAnimation
import com.boukhari.orangebank.ui.screens.shared.OrangeBankTopAppBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


/*

https://api.github.com/orgs/jetbrains/repos?page=1
full_name, forks, open_issues, watchers
 */

@Composable
fun RepoListScreen(
    viewModel: RepoListViewModel = hiltViewModel(),
    onItemClicked: (Int) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val isRefreshing by viewModel.isRefresh.collectAsState()
    val toastMessage by viewModel.showToast.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { OrangeBankTopAppBar(title = stringResource(R.string.repositories)) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {

            LaunchedEffect(toastMessage) {
                if (toastMessage.isNotEmpty()) {
                    Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
                    viewModel.onToastShown()
                }
            }
            when {
                state.value.repoList.isNotEmpty() -> Column {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                        onRefresh = { viewModel.refresh() }) {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(items = state.value.repoList, key = { it.id }) {
                                Item(item = it, onItemClicked = onItemClicked)
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider(color = Color.Gray, thickness = 1.dp)
                            }
                        }
                    }
                }
                state.value.isLoading -> Column(
                    modifier = Modifier.wrapContentHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    LoadingAnimation()
                }
                state.value.error.isNotEmpty() -> ErrorText(error = state.value.error)
            }
        }
    }
}

@Composable
fun ErrorText(error: String) {
    Text(
        text = error,
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.error,
        textAlign = TextAlign.Center,
    )
}


@Composable
fun CountIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    count: String
) {
    Row(
        modifier = modifier
            .width(56.dp)
            .wrapContentHeight()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painter,
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.CenterVertically),
            contentDescription = "countIcon",
            tint = Color.DarkGray
        )
        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = count,
            style = MaterialTheme.typography.caption,
            fontSize = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.LightGray
        )
    }
}

@Composable
fun Item(
    modifier: Modifier = Modifier,
    item: Repo,
    onItemClicked: (Int) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .clickable { onItemClicked(item.id) }) {
        Text(text = item.fullName)
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            CountIcon(
                painter = painterResource(id = R.drawable.ic_fork),
                count = item.forks.toString()
            )
            CountIcon(
                painter = painterResource(id = R.drawable.ic_open_issues),
                count = item.openIssues.toString()
            )
            CountIcon(
                painter = painterResource(id = R.drawable.ic_watcher),
                count = item.watchers.toString()
            )
        }
    }
}