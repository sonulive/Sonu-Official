package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Dilemma
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface WavelengthDao {
    @Query("SELECT * FROM dilemmas ORDER BY isDailyFeatured DESC, createdTimestamp DESC")
    fun getAllDilemmas(): Flow<List<Dilemma>>

    @Query("SELECT * FROM dilemmas WHERE isDailyFeatured = 1 LIMIT 1")
    fun getDailyFeaturedDilemma(): Flow<Dilemma?>

    @Query("SELECT * FROM dilemmas WHERE id = :id")
    suspend fun getDilemmaById(id: String): Dilemma?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDilemmas(dilemmas: List<Dilemma>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDilemma(dilemma: Dilemma)

    @Update
    suspend fun updateDilemma(dilemma: Dilemma)

    @Query("SELECT * FROM user_profile WHERE id = 'current_user' LIMIT 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfile)

    @Update
    suspend fun updateUserProfile(profile: UserProfile)
}
