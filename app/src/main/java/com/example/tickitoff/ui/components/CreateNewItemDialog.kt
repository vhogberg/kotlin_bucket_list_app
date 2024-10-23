package com.example.tickitoff.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tickitoff.events.BucketListEvent
import com.example.tickitoff.ui.theme.CustomBeige
import com.example.tickitoff.ui.theme.CustomBlue
import com.example.tickitoff.ui.theme.CustomGreen
import com.example.tickitoff.ui.theme.CustomRed
import com.example.tickitoff.viewmodel.BucketListState

// Dialog that shows up when you press FAB to create a new bucket list item/goal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewItemDialog(
    state: BucketListState,
    onEvent: (BucketListEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    BasicAlertDialog(
        onDismissRequest = {
            onEvent(BucketListEvent.HideDialogForCreatingItem)
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge,
            color = CustomBeige, // Set the background color to CustomBeige
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Title of dialog
                Text(
                    text = "Add a new goal",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = CustomBlue // Set the title color to CustomBlue
                    ),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                // For title of goal
                OutlinedTextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(BucketListEvent.SetTitle(it))
                    },
                    colors = OutlinedTextFieldDefaults.colors(CustomBlue),
                    placeholder = {
                        Text(text = "Title...", color = CustomBlue)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                // For description of goal
                OutlinedTextField(
                    value = state.description,
                    onValueChange = {
                        onEvent(BucketListEvent.SetDescription(it))
                    },
                    colors = OutlinedTextFieldDefaults.colors(CustomBlue),
                    placeholder = {
                        Text(text = "Description...", color = CustomBlue)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                // For doneByYear of goal
                OutlinedTextField(
                    value = if (state.doneByYear == 0) "" else state.doneByYear.toString(), // so it shows placeholder from beginning and not 0
                    onValueChange = { input ->
                        if (input.isEmpty()) {
                            onEvent(BucketListEvent.SetDoneByYear(2025))
                        } else {
                            try {
                                val year = input.toInt()
                                onEvent(BucketListEvent.SetDoneByYear(year))
                            } catch (e: NumberFormatException) {
                                // Handle invalid input
                            }
                        }
                    },
                    placeholder = {
                        Text(text = "Done by year... (e.g 2025)", color = CustomBlue)
                    },
                    colors = OutlinedTextFieldDefaults.colors(CustomBlue),
                    // make it so a number keyboard shows up instead of default
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onEvent(BucketListEvent.HideDialogForCreatingItem)
                    }) {
                        Text(
                            text = "Cancel",
                            color = CustomRed,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            onEvent(BucketListEvent.CreateItem) // Create the item

                            // Only show toast if user has entered something for all fields
                            if (state.title.isNotEmpty() && state.description.isNotEmpty() && state.doneByYear != 0) {
                                Toast.makeText(
                                    context,
                                    "Created the goal '${state.title}', good luck!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    ) {
                        Text(
                            text = "Save",
                            color = CustomGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}