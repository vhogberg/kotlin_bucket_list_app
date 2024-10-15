package com.example.tickitoff

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TickItOffPage(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier.fillMaxHeight()
            .padding(8.dp)

    ) {
        Greeting()
    }
}