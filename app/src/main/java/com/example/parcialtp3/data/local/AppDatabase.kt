package com.example.parcialtp3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.parcialtp3.data.local.dao.ExpenseDao
import com.example.parcialtp3.data.local.entities.ExpenseEntity

/**
 * Room Database for the application
 *
 * @Database annotation defines:
 * - entities: List of all Entity classes in the database
 * - version: Database schema version (increment when schema changes)
 * - exportSchema: Whether to export schema to a folder (useful for version control)
 */
@Database(
    entities = [
        ExpenseEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the DAO
     * Room automatically generates the implementation
     */
    abstract fun expenseDao(): ExpenseDao
}
