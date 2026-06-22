package com.example.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "consultations")
data class Consultation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val companyName: String,
    val email: String,
    val phone: String,
    val servicesSelected: String, // Comma separated list of service names
    val notes: String,
    val contactMethod: String, // "Call", "Email", "WhatsApp"
    val preferredTime: String, // e.g. "Morning", "Afternoon", "Evening"
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Pending Review" // Default status
)

@Dao
interface ConsultationDao {
    @Query("SELECT * FROM consultations ORDER BY timestamp DESC")
    fun getAllConsultations(): Flow<List<Consultation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsultation(consultation: Consultation)

    @Query("DELETE FROM consultations WHERE id = :id")
    suspend fun deleteConsultationById(id: Int)
}

@Database(entities = [Consultation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun consultationDao(): ConsultationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pallavi_agency_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class ConsultationRepository(private val consultationDao: ConsultationDao) {
    val allConsultations: Flow<List<Consultation>> = consultationDao.getAllConsultations()

    suspend fun insert(consultation: Consultation) {
        consultationDao.insertConsultation(consultation)
    }

    suspend fun deleteById(id: Int) {
        consultationDao.deleteConsultationById(id)
    }
}
