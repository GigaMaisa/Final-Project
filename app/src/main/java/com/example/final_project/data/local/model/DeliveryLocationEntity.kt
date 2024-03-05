package com.example.final_project.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_location")
data class DeliveryLocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val locationName: String,
    val locationType: LocationTypeEntity,
    val addressType: AddressTypeEntity,
    val entrance: Int,
    val floor: Int,
    val apartmentNumber: Int,
    val extraDescription: String? = null
)

enum class AddressTypeEntity {
    HOME,
    WORK,
    OTHER
}

enum class LocationTypeEntity {
    APARTMENT,
    HOUSE,
    OFFICE,
    OTHER
}
