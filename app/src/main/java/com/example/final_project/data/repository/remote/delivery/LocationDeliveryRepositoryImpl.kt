package com.example.final_project.data.repository.remote.delivery

import com.example.final_project.data.remote.common.HandleErrorStates
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.mapper.location.toDomain
import com.example.final_project.data.remote.model.delivery.LocationDeliveryDto
import com.example.final_project.domain.model.location.GetLocationDelivery
import com.example.final_project.domain.repository.location.LocationDeliveryRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationDeliveryRepositoryImpl @Inject constructor(private val databaseReference: DatabaseReference, private val ioDispatcher: CoroutineDispatcher): LocationDeliveryRepository {
    override suspend fun getCourierLocation(): Flow<Resource<GetLocationDelivery>> = callbackFlow {
        trySend(Resource.Loading(loading = true))
        databaseReference.child("deliveries").child("your_delivery_id")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val location = mutableListOf<LocationDeliveryDto>()

                    snapshot.children.forEach {
                        location.add(it.getValue(LocationDeliveryDto::class.java)!!)
                    }
                    trySend(Resource.Success(response = location.first().toDomain()))
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        trySend(Resource.Loading(loading = false))
        awaitClose {}
    }.catch {
        emit(Resource.Error(HandleErrorStates.handleException(it as Exception), throwable = it))
    }.flowOn(ioDispatcher)
}