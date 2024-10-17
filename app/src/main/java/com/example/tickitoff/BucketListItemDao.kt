package com.example.tickitoff

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BucketListItemDao {

    @Upsert // Insert if it does not exist already, updates if it does exist
    suspend fun upsertItem(bucketListItem: BucketListItem)

    @Delete
    suspend fun deleteItem(bucketListItem: BucketListItem)

    @Query("SELECT * FROM bucketListItem ORDER BY id ASC")
    fun getAllItems() : Flow<List<BucketListItem>>

}