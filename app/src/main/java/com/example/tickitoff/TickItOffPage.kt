package com.example.tickitoff

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tickitoff.ui.theme.CustomBlue
import com.example.tickitoff.ui.theme.CustomGrey


// Main page of app
@Composable
fun TickItOffPage(modifier: Modifier = Modifier) {

    var selectedOption by remember { mutableStateOf("Active") }
    val bucketList = getTestBucketListItems() // replace with non-test method

    // Filter items based on the selected option
    val filteredItems = bucketList.filter {
        if (selectedOption == "Active") !it.completed else it.completed
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            Greeting() // Greeting at the top

            // The segmented control switch to switch between completed items and non-completed ones.
            SegmentedControlSwitch(
                options = listOf("Active", "Completed"),
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }
            )

            BucketListComposable(filteredItems) // Shows the list of filtered items
        }

        // Add the FAB positioned at the bottom right
        FloatingActionButton(
            onClick = { println("FAB Clicked") },
            modifier = Modifier.align(Alignment.BottomEnd) // Align to bottom right corner
        )
    }
}

// A FAB button to add bucket list items
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
