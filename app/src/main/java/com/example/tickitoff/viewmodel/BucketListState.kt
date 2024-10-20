package com.example.tickitoff.viewmodel

import com.example.tickitoff.data.BucketListItem

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

enum class BucketListFilter {
    Active,
    Completed
}