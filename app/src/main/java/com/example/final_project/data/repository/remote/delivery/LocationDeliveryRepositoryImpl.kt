package com.example.final_project.data.repository.remote.delivery

import com.example.final_project.data.remote.common.HandleErrorStates
import com.example.final_project.data.remote.common.Resource
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
    override suspend fun getCourierLocation(): Flow<Resource<Map<String, Double>>> = callbackFlow {
        trySend(Resource.Loading(loading = true))
        databaseReference.child("deliveries").child("your_delivery_id")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val location = mutableMapOf<String, Double>()

                    snapshot.children.forEach {
                        location[it.key!!] = it.getValue(Double::class.java)!!
                    }
                    trySend(Resource.Success(response = location.toMap()))
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        trySend(Resource.Loading(loading = false))
        awaitClose {}
    }.catch {
        emit(Resource.Error(HandleErrorStates.handleException(it as Exception), throwable = it))
    }.flowOn(ioDispatcher)
}