package com.example.parcialtp3.di

import android.content.Context
import androidx.room.Room
import com.example.parcialtp3.data.local.AppDatabase
import com.example.parcialtp3.data.local.TokenManager
import com.example.parcialtp3.data.local.dao.ExpenseDao
import com.example.parcialtp3.data.remote.api.ApiService
import com.example.parcialtp3.data.repository.AuthRepositoryImpl
import com.example.parcialtp3.data.repository.ExpenseRepositoryImpl
import com.example.parcialtp3.data.repository.TransactionRepositoryImpl
import com.example.parcialtp3.domain.repository.AuthRepository
import com.example.parcialtp3.domain.repository.ExpenseRepository
import com.example.parcialtp3.domain.repository.TransactionRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Hilt Module for providing app-level dependencies
 *
 * @Module - Tells Hilt this is a dependency module
 * @InstallIn(SingletonComponent::class) - These dependencies will live as long as the application
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ==================== NETWORKING ====================

    /**
     * Provides OkHttpClient with logging interceptor
     * @Singleton ensures only one instance throughout the app
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Provides Retrofit instance
     * Base URL: FinanceApp Mock API
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://d9811bf4-5e67-4a8c-bdcf-603cbbfc0275.mock.pstmn.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides ApiService for making network calls
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    // ==================== STORAGE ====================

    /**
     * Provides TokenManager for secure token storage
     * TokenManager is automatically constructed by Hilt since it has @Inject constructor
     */
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    // ==================== DATABASE (ROOM) ====================

    /**
     * Provides Room Database instance
     * Uses KSP for annotation processing
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "parcial_database"
        )
            .fallbackToDestructiveMigration() // For development - remove in production
            .build()
    }


    @Provides
    @Singleton
    fun provideExpenseDao(database: AppDatabase): ExpenseDao {
        return database.expenseDao()
    }

    // ==================== FIREBASE ====================

    /**
     * Provides Firebase Auth instance
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    /**
     * Provides Firebase Firestore instance
     */
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    /**
     * Provides Firebase Storage instance
     */
    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    // ==================== REPOSITORY ====================

    /**
     * Provides AuthRepository for authentication operations
     */
    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        tokenManager: TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, tokenManager)
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(
        expenseDao: ExpenseDao
    ): ExpenseRepository {
        return ExpenseRepositoryImpl(expenseDao)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        apiService: ApiService
    ): TransactionRepository {
        return TransactionRepositoryImpl(apiService)
    }
}
