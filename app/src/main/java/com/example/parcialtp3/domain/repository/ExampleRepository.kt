package com.example.parcialtp3.domain.repository

import com.example.parcialtp3.domain.model.ExampleModel
import com.example.parcialtp3.domain.model.Result
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface defining the contract for data operations
 *
 * This interface is implemented in the data layer
 * and injected into ViewModels via Hilt
 */
interface ExampleRepository {

    // Local database operations
    fun getAllExamplesFromDb(): Flow<List<ExampleModel>>
    suspend fun getExampleByIdFromDb(id: Long): ExampleModel?
    suspend fun insertExample(example: ExampleModel): Long
    suspend fun updateExample(example: ExampleModel)
    suspend fun deleteExample(example: ExampleModel)

    // Remote API operations
    suspend fun fetchExamplesFromApi(): Result<List<ExampleModel>>
    suspend fun fetchExampleByIdFromApi(id: Long): Result<ExampleModel>
    suspend fun createExampleOnApi(example: ExampleModel): Result<ExampleModel>

    // Combined operations (API + Database)
    suspend fun syncExamples(): Result<List<ExampleModel>>
    fun searchExamples(query: String): Flow<List<ExampleModel>>
}
