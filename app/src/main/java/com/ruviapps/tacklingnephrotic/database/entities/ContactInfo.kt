package com.ruviapps.tacklingnephrotic.database.entities

import androidx.room.ColumnInfo

data class ContactInfo(
    @ColumnInfo("primary_contact")
    val primaryContact: String,
    @ColumnInfo("secondary_contact")
    val secondaryContact : String?,
    @ColumnInfo("email")
    val email: String?

)