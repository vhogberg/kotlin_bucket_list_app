package com.example.tickitoff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.tickitoff.ui.theme.CustomGreen

// Main part of the actual bucket list
@Composable
fun BucketListComposable(bucketList: List<BucketListItem>, state: BucketListState,
                         onEvent: (BucketListEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (state.isCreatingItem){
            AddNewItemDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(content = {
            itemsIndexed(bucketList) { index: Int, item: BucketListItem ->
                BucketListItemComposable(item = item, state = state, onEvent = onEvent)
            }
        })
    }
}

// Widget/panel style UI for the items
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
            modifier = Modifier.weight(1f) // occupies all space available
        ) {
            Text(
                text = item.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = item.description,
                fontSize = 16.sp,
                color = Color.White
            )
            if (!item.completed) {
                Text(
                    text = "Should be done by: " + item.doneByYear.toString(),
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
            else {
                Text(
                    // text below should maybe be replaced with the date when you completed it
                    text = "Congrats on completing your goal! Well done!",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
        }

        if (!item.completed){
            Column {
                // "Completed the goal" button
                IconButton(onClick = {
                    onEvent(BucketListEvent.SetCompleted(item, true))
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_check_circle_24),
                        contentDescription = "Complete item",
                        tint = Color.White
                    )
                }
                // "Want to remove the goal" button
                IconButton(onClick = {
                    onEvent(BucketListEvent.DeleteItem(item))
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Remove item",
                        tint = Color.White
                    )
                }
            }
        }
    }
}