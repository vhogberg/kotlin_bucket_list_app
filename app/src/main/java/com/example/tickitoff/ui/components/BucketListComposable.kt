package com.example.tickitoff.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tickitoff.events.BucketListEvent
import com.example.tickitoff.viewmodel.BucketListState
import com.example.tickitoff.R
import com.example.tickitoff.data.BucketListItem
import com.example.tickitoff.ui.theme.CustomGreen

// Scrollable ist of bucket list items/goals
@Composable
fun BucketListComposable(bucketList: List<BucketListItem>, state: BucketListState,
                         onEvent: (BucketListEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // If isCreatingItem is true, then show the dialog to add an item
        if (state.isCreatingItem){
            AddNewItemDialog(state = state, onEvent = onEvent)
        }

        // If isSharingItem is true, then show the dialog to share an item
        if (state.isSharingItem){
            ShareItemDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(content = {
            itemsIndexed(bucketList) { index: Int, item: BucketListItem ->
                BucketListItemComposable(item = item, state = state, onEvent = onEvent)
            }
        })
    }
}

// Singular item UI
@Composable
fun BucketListItemComposable(item: BucketListItem, state: BucketListState,
                             onEvent: (BucketListEvent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(CustomGreen)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically // Centers icons and text vertically
    ) {
        Column (
            modifier = Modifier.weight(1f) // Occupies all space available
        ) {
            // Title of goal
            Text(
                text = item.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            // Description of goal
            Text(
                text = item.description,
                fontSize = 16.sp,
                color = Color.White
            )
            // If goal is active
            if (!item.completed) {
                Text(
                    text = "Should be done by: " + item.doneByYear.toString(),
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
            // If goal is completed
            else {
                Text(
                    // text below should maybe be replaced with the date when you completed it
                    text = "Congrats on completing your goal! Well done!",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
        }

        // For active goals
        if (!item.completed){
            Column {
                // "I completed the goal" button
                IconButton(onClick = {
                    onEvent(BucketListEvent.SetCompleted(item, true)) // set isCompleted to true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_check_circle_24),
                        contentDescription = "Complete goal",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)  // Size of icon
                    )
                }
                // "I want to remove the goal" button
                IconButton(onClick = {
                    onEvent(BucketListEvent.DeleteItem(item)) // delete the item from db
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Remove goal",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)  // Size of icon
                    )
                }
            }
        }
        // For completed goals
        else {
            Column {
                IconButton(onClick = {
                    onEvent(BucketListEvent.ShowDialogForSharingItem) // open share dialog
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_ios_share_24),
                        contentDescription = "Share completed goal",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)  // Size of icon
                    )
                }
            }
        }
    }
}