package com.boukhari.orangebank.domain.model

data class Repo(
     val id: Int,
     val fullName : String,
     val forks : Int = 0,
     val openIssues : Int = 0,
     val watchers : Int = 0,
     val description : String
)
