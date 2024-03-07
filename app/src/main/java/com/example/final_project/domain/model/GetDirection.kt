package com.example.final_project.domain.model

import com.example.final_project.data.remote.model.Route

data class GetDirection(
    val routes: List<Route>,
)