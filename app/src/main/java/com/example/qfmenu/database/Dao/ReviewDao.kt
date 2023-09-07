package com.example.qfmenu.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.qfmenu.database.Entity.ReviewDb
import com.example.qfmenu.database.Entity.ReviewWithCustomerDishCrossRefs
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Transaction
    @Query("SELECT * FROM ReviewDb WHERE reviewId = :reviewId")
    fun getReviewWithCustomerDishes(reviewId: Int): Flow<ReviewWithCustomerDishCrossRefs>

    @Query("SELECT * FROM ReviewDb")
    fun getReviews(): Flow<List<ReviewDb>>

    @Query("SELECT * FROM ReviewDb WHERE reviewId = :reviewId")
    fun getReview(reviewId: Int): Flow<ReviewDb>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reviewDb: ReviewDb)

    @Update
    suspend fun update(reviewDb: ReviewDb)

    @Delete
    suspend fun delete(reviewDb: ReviewDb)

}