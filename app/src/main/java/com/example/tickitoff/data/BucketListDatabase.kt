package com.example.tickitoff.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BucketListItem::class],
    version = 2
)
abstract class BucketListDatabase: RoomDatabase() {

    abstract val dao: BucketListItemDao


}