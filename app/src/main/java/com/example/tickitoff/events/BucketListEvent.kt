package com.example.tickitoff.events

import com.example.tickitoff.data.BucketListItem

// Contains user interaction events

sealed interface BucketListEvent {
    // For creating item
    object CreateItem : BucketListEvent

    // Set values for the item to be created or updated (in case of completion of goal)
    data class SetTitle(val title: String) : BucketListEvent
    data class SetDescription(val description: String) : BucketListEvent
    data class SetDoneByYear(val doneByYear: Int) : BucketListEvent
    data class SetCompleted(val item: BucketListItem, val isCompleted: Boolean) : BucketListEvent // takes in an existing item for when isCompleted needs to be updated from false to true

    // When creating item (Clicking on FAB)
    object ShowDialogForCreatingItem : BucketListEvent
    object HideDialogForCreatingItem : BucketListEvent

    // When sharing item (Clicking on share icon in "completed" section)
    object ShowDialogForSharingItem : BucketListEvent
    object HideDialogForSharingItem : BucketListEvent

    // Show either active or completed bucket list items
    object ShowActive : BucketListEvent
    object ShowCompleted : BucketListEvent

    // For deleting item, we have to pass through an already existing item that should be deleted
    data class DeleteItem(val bucketListItem: BucketListItem) : BucketListEvent

}