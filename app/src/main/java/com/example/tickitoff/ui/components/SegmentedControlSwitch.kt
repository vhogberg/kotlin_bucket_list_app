package com.example.tickitoff.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tickitoff.ui.theme.CustomBlue
import com.example.tickitoff.ui.theme.CustomGreen

// Segmented control style switch for switching between showing active and completed bucket list items

@Composable
fun SegmentedControlSwitch(
    options: List<String>, // reusable with different options
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(CustomBlue, shape = RoundedCornerShape(16.dp))
            .padding(2.dp), // outer shell padding (blue area)
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            val backgroundColor = if (isSelected) CustomGreen else CustomBlue
            val textColor = Color.White

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(backgroundColor)
                    .clickable { onOptionSelected(option) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}