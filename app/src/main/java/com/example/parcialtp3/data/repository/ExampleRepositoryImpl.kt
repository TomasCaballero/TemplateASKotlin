package com.example.parcialtp3.data.repository

import com.example.parcialtp3.data.local.dao.ExampleDao
import com.example.parcialtp3.data.local.entities.ExampleEntity
import com.example.parcialtp3.data.remote.api.ApiService
import com.example.parcialtp3.data.remote.dto.ExampleDto
import com.example.parcialtp3.domain.model.ExampleModel
import com.example.parcialtp3.domain.model.Result
import com.example.parcialtp3.domain.repository.ExampleRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Repository implementation combining multiple data sources
 *
 * @Inject constructor tells Hilt to provide these dependencies
 */
class ExampleRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val exampleDao: ExampleDao,
    private val firestore: FirebaseFirestore
) : ExampleRepository {

    // ==================== LOCAL DATABASE OPERATIONS ====================

    override fun getAllExamplesFromDb(): Flow<List<ExampleModel>> {
        return exampleDao.getAllExamples().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override suspend fun getExampleByIdFromDb(id: Long): ExampleModel? {
        return exampleDao.getExampleById(id)?.toModel()
    }

    override suspend fun insertExample(example: ExampleModel): Long {
        return exampleDao.insert(example.toEntity())
    }

    override suspend fun updateExample(example: ExampleModel) {
        exampleDao.update(example.toEntity())
    }

    override suspend fun deleteExample(example: ExampleModel) {
        exampleDao.delete(example.toEntity())
    }

    // ==================== REMOTE API OPERATIONS ====================

    override suspend fun fetchExamplesFromApi(): Result<List<ExampleModel>> {
        return try {
            val response = apiService.getExamples()
            if (response.isSuccessful && response.body() != null) {
                val models = response.body()!!.map { it.toModel() }
                Result.Success(models)
            } else {
                Result.Error("API Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network Error: ${e.message}", e)
        }
    }

    override suspend fun fetchExampleByIdFromApi(id: Long): Result<ExampleModel> {
        return try {
            val response = apiService.getExampleById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toModel())
            } else {
                Result.Error("API Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network Error: ${e.message}", e)
        }
    }

    override suspend fun createExampleOnApi(example: ExampleModel): Result<ExampleModel> {
        return try {
            val response = apiService.createExample(example.toDto())
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toModel())
            } else {
                Result.Error("API Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network Error: ${e.message}", e)
        }
    }

    // ==================== COMBINED OPERATIONS ====================

    override suspend fun syncExamples(): Result<List<ExampleModel>> {
        return when (val result = fetchExamplesFromApi()) {
            is Result.Success -> {
                // Save to local database
                val entities = result.data.map { it.toEntity() }
                exampleDao.insertAll(entities)
                Result.Success(result.data)
            }
            is Result.Error -> result
            is Result.Loading -> Result.Loading
        }
    }

    override fun searchExamples(query: String): Flow<List<ExampleModel>> {
        return exampleDao.searchExamples(query).map { entities ->
            entities.map { it.toModel() }
        }
    }

    // ==================== FIREBASE EXAMPLE ====================

    /**
     * Example of using Firebase Firestore
     */
    suspend fun saveToFirestore(example: ExampleModel): Result<Unit> {
        return try {
            firestore.collection("examples")
                .document(example.id.toString())
                .set(example.toMap())
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("Firestore Error: ${e.message}", e)
        }
    }

    // ==================== MAPPER FUNCTIONS ====================

    /**
     * Extension function to convert Entity to Model
     */
    private fun ExampleEntity.toModel(): ExampleModel {
        return ExampleModel(
            id = id,
            title = title,
            description = description,
            createdAt = createdAt,
            isCompleted = isCompleted,
            imageUrl = imageUrl,
            authorName = null // Not stored in local database
        )
    }

    /**
     * Extension function to convert Model to Entity
     */
    private fun ExampleModel.toEntity(): ExampleEntity {
        return ExampleEntity(
            id = id,
            title = title,
            description = description,
            createdAt = createdAt,
            isCompleted = isCompleted,
            imageUrl = imageUrl
        )
    }

    /**
     * Extension function to convert DTO to Model
     */
    private fun ExampleDto.toModel(): ExampleModel {
        return ExampleModel(
            id = id,
            title = title,
            description = description,
            createdAt = createdAt,
            isCompleted = isCompleted,
            imageUrl = imageUrl,
            authorName = author?.username
        )
    }

    /**
     * Extension function to convert Model to DTO
     */
    private fun ExampleModel.toDto(): ExampleDto {
        return ExampleDto(
            id = id,
            title = title,
            description = description,
            createdAt = createdAt,
            isCompleted = isCompleted,
            imageUrl = imageUrl,
            author = null // Not needed when creating
        )
    }

    /**
     * Extension function to convert Model to Map for Firestore
     */
    private fun ExampleModel.toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "description" to description,
            "createdAt" to createdAt,
            "isCompleted" to isCompleted,
            "imageUrl" to imageUrl,
            "authorName" to authorName
        )
    }
}
