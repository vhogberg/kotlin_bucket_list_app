package com.example.tickitoff.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tickitoff.R
import com.example.tickitoff.ui.theme.CustomBlue

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        // Text that should switch between "Good morning!", "Good afternoon!", "Good evening!" depending on TOD.
        Text(
            text = "Good morning!",
            color = CustomBlue,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
        )
        GreetingImage()
    }
}

// Show the logo for the app
@Composable
fun GreetingImage(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.tickitofflogo)
    Row {
        Spacer(modifier.padding(8.dp))
        Image(
            painter = image,
            contentDescription = null
        )
        Spacer(
            modifier
                .padding(8.dp)
                .width(8.dp))
    }
}