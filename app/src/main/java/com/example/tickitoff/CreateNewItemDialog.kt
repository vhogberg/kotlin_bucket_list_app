package com.example.tickitoff

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tickitoff.ui.theme.CustomBlue
import com.example.tickitoff.ui.theme.CustomGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewItemDialog(
    state: BucketListState,
    onEvent: (BucketListEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    BasicAlertDialog(
        onDismissRequest = {
            onEvent(BucketListEvent.HideDialog)
        }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add new bucket list goal",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(BucketListEvent.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = "Title...")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = state.description,
                    onValueChange = {
                        onEvent(BucketListEvent.SetDescription(it))
                    },
                    placeholder = {
                        Text(text = "Description...")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = state.doneByYear.toString(),
                    onValueChange = { input ->
                        if (input.isEmpty()) {
                            onEvent(BucketListEvent.SetDoneByYear(0))
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
                        Text(text = "Done by year... (e.g 2025)")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onEvent(BucketListEvent.HideDialog)
                    } ) {
                        Text(
                            text = "Cancel",
                            color = Color(0xFF931515)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            onEvent(BucketListEvent.CreateItem)
                        }
                    ) {
                        Text(
                            text = "Save",
                            color = CustomGreen
                        )
                    }
                }
            }
        }
    }
}




// Egen basicalertdialog funktion med två val
@OptIn(ExperimentalMaterial3Api::class) // experimentell funktion i compose, därför behövs detta
@Composable
fun MyFirstAlertDialog(
    onDismiss: () -> Unit, // hantera vad som händer när man trycker bort dialogen
    title: String, // titeln på dialogrutan
    confirmText: String, // knapptext för att bekräfta, "Okej"
    dismissText: String, // knapptext för att avbryta/neka dialogen, "Avbryt"
    onConfirm: () -> Unit // hantera vad som händer om man trycker på OK knappen.
) {

    BasicAlertDialog(
        onDismissRequest = {  }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large, // formen
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title, // visa titeltexten
                    style = MaterialTheme.typography.headlineSmall // storlek titel
                )
                Spacer(modifier = Modifier.height(8.dp))
                // myInputTextField("Set title")
                Spacer(modifier = Modifier.height(8.dp))
                // myInputTextField("Set description")
                Spacer(modifier = Modifier.height(8.dp))
                // myInputTextField("Set year it should be done by")

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End // gör att knapparna hamnar till höger
                ) {
                    TextButton(
                        onClick = onDismiss // stäng dialogen när användaren klickar på avbryt-knappen
                    ) {
                        Text(
                            dismissText,
                            color = Color(0xFF000099)
                        ) // texten och färgen på avbryt-knappen
                    }
                    Spacer(modifier = Modifier.width(8.dp)) // mellanrum mellan knapparna
                    TextButton(
                        onClick = {
                            onConfirm() // kör bekräftelse-funktionen
                            onDismiss() // stäng också dialogen efter att ha bekräftat
                        }
                    ) {
                        Text(
                            confirmText,
                            color = Color(0xFF000099)
                        ) // texten och färgen på bekräftelse-knappen
                    }
                }
            }
        }
    }
}

@Composable
fun MyInputTextField(
    label: String,
    text: String,
    onTextChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { focusManager.clearFocus() }
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text(label) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(CustomBlue)
        )
    }
}


// Blått text fält för input
// används på detta vis:
// message = myInputTextField("Ange meddelandet: ")
@Composable
fun myFirstInputTextField(label: String): String { // composable funktioner med retur ska ej kapitaliseras
    // sträng som kan uppdateras
    var outputText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current // för att kunna använda clearfocus
    // Box används för att detektera klick utanför tangentbords-popupen
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { focusManager.clearFocus() } // om man klickar utanför tangentbordet ( i boxen) stängs tangentbordet
    ) {
        // OutlinedTextField är en variant på TextField som ser lite snyggare ut
        OutlinedTextField(
            // dekoration v
            modifier = Modifier
                .padding(16.dp) // padding till kant så inte knappen täcker hela hela skärmen
                .fillMaxWidth(), // täck hela skärmen, förutom de 16.dp vi sa ovan.
            shape = RoundedCornerShape(12.dp), // formen, 12.dp är radie på hörnen.
            colors = OutlinedTextFieldDefaults.colors(CustomBlue), // blå färg

            // funktionalitet v
            value = outputText,
            onValueChange = { outputText = it }, // uppdatera outputText baserat på vad som skrivs
            label = { Text(label) }, // label som beskrivning för användaren, skickas med som parameter
        )
    }
    // returnera det som skrivits.
    return outputText
}