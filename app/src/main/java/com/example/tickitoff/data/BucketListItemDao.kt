package com.example.tickitoff.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

// Dao (Data access object), interface to our Room DB.

@Dao
interface BucketListItemDao {

    @Upsert // Insert if it does not exist already, updates if it does exist, we for example use upsert to update isCompleted variable.
    suspend fun upsertItem(bucketListItem: BucketListItem)

    @Delete
    suspend fun deleteItem(bucketListItem: BucketListItem)

    @Query("SELECT * FROM bucketListItem ORDER BY id ASC")
    fun getAllItems(): Flow<List<BucketListItem>>

}