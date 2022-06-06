package com.ruviapps.tacklingnephrotic.database.entities

import androidx.room.*
import com.ruviapps.tacklingnephrotic.domain.NephroticState
import java.time.LocalDate
import java.util.*

@Entity(tableName = TableName.StateTable, foreignKeys = [ForeignKey(
    entity = DatabasePatient::class,
    parentColumns = [DatabasePatient.ColumnPatientId],
    childColumns = [DatabasePatientState.ColumnPatientId],
    onDelete = ForeignKey.CASCADE
)])
data class DatabasePatientState(
    @PrimaryKey(true)
    @ColumnInfo(ColumnStateId)
    val stateId : Long,
    @ColumnInfo(ColumnState)
    val patient_state : NephroticState,
    @ColumnInfo(ColumnPatientId,index=true)
    val patientId : Long,
    @ColumnInfo(ColumnOnDate)
    val onDate : Date,

    ){
    companion object{
        const val ColumnStateId ="state_id"
        const val ColumnState = "patient_state"
        const val ColumnPatientId = "to_patient"
        const val ColumnOnDate = "on_date"
    }
}

data class PatientWithStates(
    @Embedded val patient: DatabasePatient,
    @Relation(
        parentColumn = DatabasePatient.ColumnPatientId,
        entityColumn = DatabasePatientState.ColumnPatientId
    )
    val states : List<DatabasePatientState>
)