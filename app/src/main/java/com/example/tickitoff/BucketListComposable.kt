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
fun BucketListComposable() {

    // Get the list of items
    val bucketList = getTestBucketListItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Display the items in a scrollable column
        LazyColumn(content = {
            itemsIndexed(bucketList) { index: Int, item: BucketListItem ->
                BucketListItemComposable(item = item)
            }
        })
    }
}

// Widget/panel style UI for the items
@Composable
fun BucketListItemComposable(item: BucketListItem) {
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
            modifier = Modifier.weight(1f)
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
            Text(text = "Should be done by: " + item.doneByYear.toString(),
                fontSize = 14.sp,
                color = Color.LightGray)
        }

        Column {
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                    contentDescription = "Complete item",
                    tint = Color.White
                )
            }
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Remove item",
                    tint = Color.White
                )
            }
        }
    }
}