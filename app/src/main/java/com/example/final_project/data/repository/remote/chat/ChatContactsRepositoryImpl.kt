package com.example.final_project.data.repository.remote.chat

import com.example.final_project.data.remote.common.HandleErrorStates
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.mapper.chat.toData
import com.example.final_project.data.remote.mapper.chat.toDomain
import com.example.final_project.data.remote.model.ContactDto
import com.example.final_project.domain.model.chat.GetContact
import com.example.final_project.domain.repository.chat.ChatContactsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChatContactsRepositoryImpl @Inject constructor(private val databaseReference: DatabaseReference) :
    ChatContactsRepository {

    private val senderUid = FirebaseAuth.getInstance().currentUser?.uid

    override suspend fun getContacts(): Flow<Resource<List<GetContact>>> =
        callbackFlow {
            trySend(Resource.Loading(loading = true))
            senderUid?.let {
                databaseReference.child("contacts").child(it)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val listOfContacts = mutableListOf<ContactDto>()
                            snapshot.children.forEach {
                                listOfContacts.add(it.getValue(ContactDto::class.java)!!)
                            }

                            trySend(Resource.Success(response = listOfContacts.map { it.toDomain() }))
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
            }
            trySend(Resource.Loading(loading = false))
            awaitClose {

            }
        }.catch {
            emit(Resource.Error(HandleErrorStates.handleException(it as Exception), throwable = it))
        }.flowOn(IO)

    override suspend fun addContact(contact: GetContact) {
        senderUid?.let {uid ->
            val contactId = databaseReference.child("contacts").child(uid).push().key
            contactId?.let {
                val contactRef = databaseReference.child("contacts").child(uid).child(it)

                contactRef.setValue(contact.toData())
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener { _ ->

                    }
            }
        }
    }
}