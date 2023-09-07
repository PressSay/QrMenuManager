package com.example.qfmenu.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.qfmenu.database.Entity.CustomerDishCrossRef
import com.example.qfmenu.database.Entity.ReviewCustomerCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewCustomerCrossRefDao {

    @Query("SELECT * FROM ReviewCustomerCrossRef")
    fun getReviewCustomerCrossRefs(): Flow<List<ReviewCustomerCrossRef>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reviewCustomerCrossRef: ReviewCustomerCrossRef)

    @Update
    suspend fun update(reviewCustomerCrossRef: ReviewCustomerCrossRef)

    @Delete
    suspend fun delete(reviewCustomerCrossRef: ReviewCustomerCrossRef)

}