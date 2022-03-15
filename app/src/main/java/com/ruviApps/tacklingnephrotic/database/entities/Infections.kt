package com.ruviApps.tacklingnephrotic.database.entities

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import java.util.*


@Entity(tableName = TableName.InfectionTable, foreignKeys = [ForeignKey(
    entity = DatabasePatient::class,
    parentColumns = [DatabasePatient.ColumnPatientId],
    childColumns = [Infections.ColumnToPatientId],
    onDelete = CASCADE
),ForeignKey(
    entity = Diseases::class,
    parentColumns = [Diseases.ColumnDiseaseCode],
    childColumns = [Infections.ColumnDiseaseId],
    onDelete = CASCADE
)],
)
data class Infections(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ColumnInfectionsId)
    val infectionId :Long,
    @ColumnInfo(ColumnToPatientId)
    val toPatient: Long,
    @ColumnInfo(ColumnDiseaseId)
    val diseasesID : Long?,
    @ColumnInfo(ColumnStartDate)
    val startDate : Date,
    @ColumnInfo(ColumnEndDate)
    val endDate: Date,
    @ColumnInfo(ColumnCurrentState)
    val CurrentState: DiseasesState
){
    companion object{
        const val ColumnInfectionsId = "infection_id"
        const val ColumnToPatientId = "to_patient_id"
        const val ColumnDiseaseId = "what_disease_id"
        const val ColumnStartDate = "start_date"
        const val ColumnEndDate = "end_date"
        const val ColumnCurrentState = "current_state"
    }
}

enum class DiseasesState{
    UNDER_TREATMENT,
    RECOVERED
}
/**
 * patient can have multiple disease so one to many relationship
 *
 */
data class PatientWithInfections(
    @Embedded val patient: DatabasePatient,
    @Relation(
        parentColumn = DatabasePatient.ColumnPatientId,
        entityColumn = Infections.ColumnToPatientId
            )
    val infectionsList : List<Infections>
)

//in dao usage
/*
@Transaction
@Query("Select * from Patient where patient_id = :patientId )
fun getPatientWithOtherDiseaseDetails(patientId : Long): List<PatientWithOtherDiseaseToPatient>
 */

/**
 * For each disease there can be previous history record which are recorded in Infection Table
 */
data class DiseaseWithInfectionsToPatient(
    @Embedded val diseases: Diseases,
    @Relation(
        parentColumn = Diseases.ColumnDiseaseCode,
        entityColumn = Infections.ColumnDiseaseId
    )
    val infectionsList : List<Infections>
)

//in dao usage
/**
 * @Transaction
 * @Query("Select * from Diseases where disease_code = :diseaseCode)
 * fun getDiseaseWithOtherDiseasesToPatient(diseaseCode :Long) : List<DiseasesWithOtherDiseasesToPatient>
 */


