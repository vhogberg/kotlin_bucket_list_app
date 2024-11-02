package com.example.tickitoff.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Our data class for the actual bucket list goals

@Entity
data class BucketListItem(
    val title: String, // Title of the goal such as "Go to Paris"
    val description: String, // Description of goal with more details such as "Save money X amount of money"
    val doneByYear: Int, // User should set a year they want the goal to be done by
    val completed: Boolean, // Boolean that tracks whether it is completed or not.
    @PrimaryKey(autoGenerate = true) // Create keys automatically, starts at 0.
    val id: Int = 0,
)