package com.boukhari.orangebank.ui.screens.navigation


const val REPO_LIST_SCREEN_ROUTE = "repo_list_screen"
const val REPO_DETAIL_SCREEN_ROUTE = "repo_detail_screen"
const val REPO_DETAIL_ID_EXTRA = "id"

sealed class Screen(val route: String){
    object RepoListScreen: Screen(REPO_LIST_SCREEN_ROUTE)
    object DetailScreen: Screen(REPO_DETAIL_SCREEN_ROUTE)
}
