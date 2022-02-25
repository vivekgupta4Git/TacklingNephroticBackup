package com.ruviApps.tacklingnephrotic.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CareTaker(
    @PrimaryKey(true)
    @ColumnInfo("caretaker_id")
    val ctId : Long,
    @ColumnInfo("first_name")
    val firstName : String,
    @ColumnInfo("last_name")
    val lastName  : String?,
    @Embedded val contact : ContactInfo,

)

data class ContactInfo(
    @ColumnInfo("primary_contact")
    val primaryContact: Int,
    @ColumnInfo("secondary_contact")
    val secondaryContact : Int?,
    @ColumnInfo("email")
    val email: String?

)