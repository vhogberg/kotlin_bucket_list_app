package com.example.tickitoff.viewmodel

import com.example.tickitoff.data.BucketListItem

// State management for setting certain values and checking whether user is currently in a certain interaction

data class BucketListState(
    val bucketListItems: List<BucketListItem> = emptyList(),
    val title: String = "",
    val description: String = "",
    val doneByYear: Int = 0,
    val isCompleted: Boolean = false,
    val isCreatingItem: Boolean = false, // checks if user is in the menu to add new bucket list item
    val isSharingItem: Boolean = false, // checks if user is in the menu to share a bucket list item
    val selectedItemTitle: String? = null, // to hold the currently clicked item's title for sharing
    val filter: BucketListFilter = BucketListFilter.Active // Default to active filter
)

// Filter for showing either active or completed items
enum class BucketListFilter {
    Active,
    Completed
}