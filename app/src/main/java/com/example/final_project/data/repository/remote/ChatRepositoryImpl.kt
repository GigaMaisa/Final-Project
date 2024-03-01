package com.example.final_project.data.repository.remote

import com.example.final_project.data.remote.common.HandleErrorStates
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.mapper.toDomain
import com.example.final_project.data.remote.model.MessageDto
import com.example.final_project.domain.model.GetMessage
import com.example.final_project.domain.repository.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class ChatRepositoryImpl @Inject constructor() : ChatRepository {
    override suspend fun getMessages(receiverUuid: String): Flow<Resource<List<GetMessage>>> = flow {
        emit(Resource.Loading(loading = true))

        val senderRoom = receiverUuid.plus(FirebaseAuth.getInstance().currentUser?.uid)
        val databaseReference = FirebaseDatabase.getInstance().reference.child("chats").child(senderRoom).child("messages")

        val listOfMessages = suspendCoroutine<List<MessageDto>> { continuation ->
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfMessages = mutableListOf<MessageDto>()
                    snapshot.children.forEach {
                        listOfMessages.add(it.getValue(MessageDto::class.java)!!)
                    }
                    continuation.resumeWith(Result.success(listOfMessages))
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWith(Result.failure(error.toException()))
                }
            })
        }
        emit(Resource.Success(response = listOfMessages.map { it.toDomain() }))
    }.catch {
        emit(Resource.Error(error = HandleErrorStates.handleException(it), throwable = it))
    }.onCompletion {
        emit(Resource.Loading(loading = false))
    }

    override suspend fun addMessage(message: GetMessage) {

    }
}