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
import java.util.Calendar

// Greeting at the top of the screen with dynamic text and logo of app

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Text switches between "Good morning!", "Good afternoon!", "Good evening!" depending on the TOD.
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greetingText = when (currentHour) {
            in 12..16 -> "Good afternoon!"
            in 17..23 -> "Good evening!"
            else -> "Good morning!"
        }
        Text(
            text = greetingText,
            color = CustomBlue,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(
                horizontal = 8.dp, vertical = 8.dp
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
            painter = image, contentDescription = null
        )
        Spacer(
            modifier
                .padding(8.dp)
                .width(8.dp)
        )
    }
}