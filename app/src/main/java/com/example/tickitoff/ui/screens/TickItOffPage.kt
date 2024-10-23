package com.example.tickitoff.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tickitoff.events.BucketListEvent
import com.example.tickitoff.ui.components.AddNewItemDialog
import com.example.tickitoff.ui.components.BucketListComposable
import com.example.tickitoff.ui.components.Greeting
import com.example.tickitoff.ui.components.SegmentedControlSwitch
import com.example.tickitoff.ui.components.ShareItemDialog
import com.example.tickitoff.ui.theme.CustomBlue
import com.example.tickitoff.ui.theme.CustomGrey
import com.example.tickitoff.viewmodel.BucketListFilter
import com.example.tickitoff.viewmodel.BucketListState


// Main page of app

@Composable
fun TickItOffPage(
    state: BucketListState,
    onEvent: (BucketListEvent) -> Unit
) {

    // Holds whatever option in the segmented control switch we currently have selected, i.e either "Active" or "Completed"
    var selectedOption by remember { mutableStateOf("Active") }

    // Filter the items based on whatever option in the segmented control switch we currently have selected
    val filteredItems = state.bucketListItems.filter {
        when (state.filter) {
            BucketListFilter.Active -> !it.completed // Show only active items
            BucketListFilter.Completed -> it.completed // Show only completed items
        }
    }

    // If isCreatingItem is true, then show the dialog to add an item
    if (state.isCreatingItem) {
        AddNewItemDialog(state = state, onEvent = onEvent)
    }
    // If isSharingItem is true, then show the dialog to share an item and pass the title of that item
    if (state.isSharingItem) {
        state.selectedItemTitle?.let { title ->
            ShareItemDialog(title = title, onEvent = onEvent)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp)
        ) {
            // Spacer at the top so greeting is not hidden behind camera punch hole
            Spacer(modifier = Modifier.height(40.dp))

            Greeting() // Greeting at the top

            // The segmented control switch to switch between completed items and non-completed ones.
            SegmentedControlSwitch(
                options = listOf("Active", "Completed"),
                selectedOption = selectedOption,
                onOptionSelected = { option ->
                    selectedOption = option
                    // Debugging
                    Log.d("TickItOffPage", "Selected option changed to: $selectedOption")
                    onEvent(
                        when (option) {
                            "Active" -> BucketListEvent.ShowActive
                            "Completed" -> BucketListEvent.ShowCompleted
                            else -> throw IllegalArgumentException("Unknown option")
                        }
                    )
                }
            )

            if (filteredItems.isNotEmpty()) {
                // Show the list of filtered items if there are any
                BucketListComposable(bucketList = filteredItems, onEvent = onEvent)
            } else when (selectedOption) { // If the list is empty
                "Active" -> {
                    // Show instructions for adding items if there are no active items
                    Column {
                        ShowExampleListOfItems()
                    }
                }

                "Completed" -> {
                    // Show instructions for completing items if there are no completed items
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "To mark a goal as complete, please click the checkmark next to the goal!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = CustomBlue,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Adding the FAB positioned at the bottom right
        FloatingActionButton(
            onClick = {
                // Open dialog to create item with event
                onEvent(BucketListEvent.ShowDialogForCreatingItem)
            },
            modifier = Modifier.align(Alignment.BottomEnd) // Align to bottom right corner
        )
    }
}

// A FAB to add bucket list items
@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LargeFloatingActionButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(30.dp), // Rounded corners on the FAB
        contentColor = Color.White, // (the icon)
        containerColor = CustomBlue, // (the background)
        modifier = modifier.padding(16.dp) // Padding for spacing from the edges
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add bucket list item")
    }
}

@Composable
fun ShowExampleItem(title: String, description: String, doneByYear: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(CustomGrey)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically // Centers icons and text vertically
    ) {
        Column(
            modifier = Modifier.weight(1f) // Occupies all space available
        ) {
            // Title of goal
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            // Description of goal
            Text(
                text = description,
                fontSize = 16.sp,
                color = Color.White
            )

                Text(
                    text = "Should be done by: $doneByYear",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
        }
    }
}

// Show the example items in a list
@Composable
fun ShowExampleListOfItems() {
    // Generate the items
    val exampleItems = listOf(
        Triple("Go to Paris", "I need to save up at least 5000 dollars and find someone to travel with", 2025),
        Triple("Run a marathon", "Start a training regimen and sign up for a local marathon event", 2026),
        Triple("Climb Mount Kilimanjaro", "Train for high-altitude hiking and book a guided expedition", 2027)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "To create a bucket list goal, please click the blue plus button in the bottom right!" +
                    "\n\nIf you are in need of ideas, here are some examples below:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = CustomBlue,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Show each item in a scrollable column
            items(exampleItems) { (title, description, year) ->
                ShowExampleItem(
                    title = title,
                    description = description,
                    doneByYear = year
                )
            }
        }
    }
}