package com.example.parcialtp3.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.parcialtp3.data.local.entities.ExampleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for ExampleEntity
 *
 * @Dao annotation marks this as a Room DAO
 * All methods are processed by KSP to generate implementation code
 */
@Dao
interface ExampleDao {

    /**
     * Insert a new example
     * OnConflictStrategy.REPLACE will replace if ID already exists
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(example: ExampleEntity): Long

    /**
     * Insert multiple examples
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(examples: List<ExampleEntity>)

    /**
     * Update an existing example
     */
    @Update
    suspend fun update(example: ExampleEntity)

    /**
     * Delete an example
     */
    @Delete
    suspend fun delete(example: ExampleEntity)

    /**
     * Get all examples as Flow (reactive)
     * Flow emits new values whenever the data changes
     */
    @Query("SELECT * FROM examples ORDER BY created_at DESC")
    fun getAllExamples(): Flow<List<ExampleEntity>>

    /**
     * Get example by ID
     */
    @Query("SELECT * FROM examples WHERE id = :id")
    suspend fun getExampleById(id: Long): ExampleEntity?

    /**
     * Get completed examples
     */
    @Query("SELECT * FROM examples WHERE is_completed = 1 ORDER BY created_at DESC")
    fun getCompletedExamples(): Flow<List<ExampleEntity>>

    /**
     * Get pending examples
     */
    @Query("SELECT * FROM examples WHERE is_completed = 0 ORDER BY created_at DESC")
    fun getPendingExamples(): Flow<List<ExampleEntity>>

    /**
     * Delete all examples
     */
    @Query("DELETE FROM examples")
    suspend fun deleteAll()

    /**
     * Search examples by title
     * Using LIKE for pattern matching
     */
    @Query("SELECT * FROM examples WHERE title LIKE '%' || :searchQuery || '%'")
    fun searchExamples(searchQuery: String): Flow<List<ExampleEntity>>
}
