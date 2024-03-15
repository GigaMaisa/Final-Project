package com.example.final_project.data.remote.mapper.distance

import com.example.final_project.data.remote.model.distance.DistanceMatrixDto
import com.example.final_project.domain.model.distance.GetDistance

fun DistanceMatrixDto.toDomain() = GetDistance(
    distance = rows.firstOrNull()?.elements?.firstOrNull()?.distance?.text ?: "",
    duration = rows.firstOrNull()?.elements?.firstOrNull()?.duration?.text ?: ""
)