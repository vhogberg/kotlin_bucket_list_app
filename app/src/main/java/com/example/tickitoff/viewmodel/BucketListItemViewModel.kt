package com.example.tickitoff.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tickitoff.data.BucketListItem
import com.example.tickitoff.data.BucketListItemDao
import com.example.tickitoff.events.BucketListEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BucketListItemViewModel(
    private val dao: BucketListItemDao
) : ViewModel() {

    private val _state = MutableStateFlow(BucketListState())
    val state: StateFlow<BucketListState> = _state.asStateFlow().
    stateIn(viewModelScope, SharingStarted.WhileSubscribed(6000), BucketListState()) // This creates an immutable StateFlow that external classes can observe but cannot modify directly.

    init {
        viewModelScope.launch {
            dao.getAllItems().collect { items ->
                _state.update { it.copy(bucketListItems = items) }
            }
        }
    }

    fun onEvent(event: BucketListEvent) {
        when (event) {
            BucketListEvent.CreateItem -> {
                val title = state.value.title
                val description = state.value.description
                val doneByYear = state.value.doneByYear

                if(title.isBlank() || description.isBlank() || doneByYear == 0){
                    return // we don't have anything to insert
                }

                val bucketListItem = BucketListItem(
                    title = title,
                    description = description,
                    doneByYear = doneByYear,
                    completed = false,
                )

                viewModelScope.launch {
                    dao.upsertItem(bucketListItem)
                }
                _state.update { it.copy (
                    isCreatingItem = false,
                    title = "",
                    description = "",
                    doneByYear = 0
                ) }
            }

            is BucketListEvent.DeleteItem -> {
                viewModelScope.launch { dao.deleteItem(event.bucketListItem) }

            }

            BucketListEvent.HideDialogForCreatingItem -> {
                _state.update { it.copy (
                    isCreatingItem = false
                ) }
            }

            is BucketListEvent.SetDescription -> {
                _state.update { it.copy(
                    description = event.description
                ) }
            }

            is BucketListEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            BucketListEvent.ShowDialogForCreatingItem -> {
                _state.update { it.copy (
                    isCreatingItem = true
                ) }
            }

            is BucketListEvent.SetDoneByYear -> {
                _state.update { it.copy(
                    doneByYear = event.doneByYear
                ) }
            }

            is BucketListEvent.ShowActive -> {
                // Update the filter to show active items
                _state.value = state.value.copy(filter = BucketListFilter.Active)
            }

            is BucketListEvent.ShowCompleted -> {
                // Update the filter to show completed items
                _state.value = state.value.copy(filter = BucketListFilter.Completed)
            }

            is BucketListEvent.SetCompleted -> {
                // Update the in-memory state
                _state.update { currentState ->
                    val updatedItems = currentState.bucketListItems.map { bucketItem ->
                        if (bucketItem.id == event.item.id) {
                            bucketItem.copy(completed = event.isCompleted)
                        } else {
                            bucketItem
                        }
                    }
                    currentState.copy(bucketListItems = updatedItems)
                }

                // Update the item in the database
                viewModelScope.launch {
                    val updatedItem = event.item.copy(completed = event.isCompleted)
                    dao.upsertItem(updatedItem)
                }
            }

            BucketListEvent.ShowDialogForSharingItem -> {
                _state.update { it.copy (
                    isSharingItem = true
                ) }
            }

            BucketListEvent.HideDialogForSharingItem -> {
                _state.update { it.copy (
                    isSharingItem = false
                ) }
            }
        }
    }
}