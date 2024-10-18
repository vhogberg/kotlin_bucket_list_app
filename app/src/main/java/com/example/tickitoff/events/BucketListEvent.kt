package com.example.tickitoff.events

import com.example.tickitoff.data.BucketListItem


/*
Contains user interaction events
 */

sealed interface BucketListEvent {
    object CreateItem: BucketListEvent

    data class SetTitle(val title: String): BucketListEvent
    data class SetDescription(val description: String): BucketListEvent
    data class SetDoneByYear(val doneByYear: Int): BucketListEvent
    data class SetCompleted(val item: BucketListItem, val isCompleted: Boolean): BucketListEvent

    object ShowDialog: BucketListEvent
    object HideDialog: BucketListEvent

    object ShowActive : BucketListEvent
    object ShowCompleted : BucketListEvent

    data class DeleteItem(val bucketListItem: BucketListItem): BucketListEvent

}