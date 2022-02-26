package com.ruviApps.tacklingnephrotic.database.entities

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import java.util.*


@Entity(tableName = TableName.OtherDiseasesToPatientTable, foreignKeys = [ForeignKey(
    entity = DatabasePatient::class,
    parentColumns = [DatabasePatient.ColumnPatientId],
    childColumns = [OtherDiseasesToPatient.ColumnToPatientId],
    onDelete = CASCADE
),ForeignKey(
    entity = DatabaseDiseases::class,
    parentColumns = [DatabaseDiseases.ColumnDiseaseCode],
    childColumns = [OtherDiseasesToPatient.ColumnDiseaseId],
    onDelete = CASCADE
)],
)
data class OtherDiseasesToPatient(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ColumnOtherDiseasesToPatientId)
    val otherDiseaseDetailsId :Long,
    @ColumnInfo(ColumnToPatientId)
    val toPatient: Long,
    @ColumnInfo(ColumnDiseaseId)
    val diseasesID : Long,
    @ColumnInfo("start_date")
    val startDate : Date,
    @ColumnInfo("end_date")
    val endDate: Date,
    @ColumnInfo("current_state")
    val CurrentState: DiseasesState
){
    companion object{
        const val ColumnOtherDiseasesToPatientId = "odd_id"
        const val ColumnToPatientId = "to_patient_id"
        const val ColumnDiseaseId = "what_disease_id"
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
data class PatientWithOtherDiseaseToPatient(
    @Embedded val patient: DatabasePatient,
    @Relation(
        parentColumn = DatabasePatient.ColumnPatientId,
        entityColumn = OtherDiseasesToPatient.ColumnToPatientId
            )
    val allDiseasesDetailsOfPatient : List<OtherDiseasesToPatient>
)

//in dao usage
/*
@Transaction
@Query("Select * from Patient where patient_id = :patientId )
fun getPatientWithOtherDiseaseDetails(patientId : Long): List<PatientWithOtherDiseaseToPatient>
 */

/**
 * For each disease there can be previous history record which are recorded in OtherDiseasesToPatient
 */
data class DiseaseWithOtherDiseasesToPatient(
    @Embedded val diseases: DatabaseDiseases,
    @Relation(
        parentColumn = DatabaseDiseases.ColumnDiseaseCode,
        entityColumn = OtherDiseasesToPatient.ColumnDiseaseId
    )
    val diseasesToPatientDetails : List<OtherDiseasesToPatient>
)

//in dao usage
/**
 * @Transaction
 * @Query("Select * from Diseases where disease_code = :diseaseCode)
 * fun getDiseaseWithOtherDiseasesToPatient(diseaseCode :Long) : List<DiseasesWithOtherDiseasesToPatient>
 */


