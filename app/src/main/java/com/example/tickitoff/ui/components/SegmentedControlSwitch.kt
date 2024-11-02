package com.example.tickitoff.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.tickitoff.ui.theme.CustomBlue
import com.example.tickitoff.ui.theme.CustomGreen

// Animated segmented control menu switch
// Will use a spring animation

@Composable
fun SegmentedControlSwitch(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val selectedIndex = options.indexOf(selectedOption)
    val targetIndex = remember { mutableFloatStateOf(selectedIndex.toFloat()) }
    var containerWidth by remember { mutableIntStateOf(0) }

    // Animate the position
    val animatedPosition by animateFloatAsState(
        targetValue = targetIndex.floatValue,
        animationSpec = spring(
            dampingRatio = 0.6f,
            stiffness = Spring.StiffnessMedium
        ),
        label = "selection position segmented control switch"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Blue background container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomBlue, shape = RoundedCornerShape(16.dp))
                .padding(2.dp)  // Padding for the blue background
                .onSizeChanged { containerWidth = it.width }
        ) {
            // Animated selection indicator (green slider)
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f / options.size)
                    .offset {
                        val itemWidth =
                            (containerWidth - 4.dp.toPx()) / options.size // Subtract padding from container width
                        IntOffset(
                            x = (animatedPosition * itemWidth).toInt(),
                            y = 0
                        )
                    }
                    .padding(2.dp)  // Padding for the green slider
                    .clip(RoundedCornerShape(16.dp))
                    .background(CustomGreen)
                    .height(40.dp)

            )

            // Text options layered above the green slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // List of options, in our case we always have just 2, but it is possible to have more.
                options.forEachIndexed { index, option ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                targetIndex.floatValue = index.toFloat()
                                onOptionSelected(option)
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
