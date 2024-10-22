package com.example.tickitoff.data

import androidx.room.Database
import androidx.room.RoomDatabase

// db

@Database(
    entities = [BucketListItem::class],
    version = 1 // ++change to update to new clean database
)
abstract class BucketListDatabase : RoomDatabase() {

    abstract val dao: BucketListItemDao


}