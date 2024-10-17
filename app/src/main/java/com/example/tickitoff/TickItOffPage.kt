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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tickitoff.ui.theme.CustomGrey


// Main page of app
@Composable
fun TickItOffPage(modifier: Modifier = Modifier) {
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
            BucketListComposable()
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
        containerColor = CustomGrey, // (the background)
        modifier = modifier.padding(20.dp) // Padding for spacing from the edges
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add bucket list item")
    }
}
