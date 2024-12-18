package com.example.tickitoff.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tickitoff.R
import com.example.tickitoff.events.BucketListEvent
import com.example.tickitoff.ui.theme.CustomBeige
import com.example.tickitoff.ui.theme.CustomBlue
import com.example.tickitoff.ui.theme.CustomRed
import com.example.tickitoff.utils.isConnectedToInternet


// Dialog that shows up when you press a share button to share a bucket list item/goal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareItemDialog(
    title: String,
    onEvent: (BucketListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    BasicAlertDialog(
        onDismissRequest = {
            onEvent(BucketListEvent.HideDialogForSharingItem)
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
                Text(
                    text = "Share your completed goal via:",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = CustomBlue // Set the title color to CustomBlue
                    ),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween // This will space the items evenly
                ) {
                    // SMS Button
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f) // This ensures each column takes up equal space
                            .clickable(onClick = {
                                shareViaSMS(context, title)
                            })
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sms_24),
                            contentDescription = "Share via SMS",
                            tint = CustomBlue,
                            modifier = Modifier
                                .size(52.dp)
                        )
                        Text(
                            text = "SMS",
                            textAlign = TextAlign.Center,
                            color = CustomBlue
                        )
                    }

                    // Email Button
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = {
                                shareViaEmail(context, title)
                            })
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_email_24),
                            contentDescription = "Share via Email",
                            tint = CustomBlue,
                            modifier = Modifier
                                .size(52.dp)
                        )
                        Text(
                            text = "Email",
                            textAlign = TextAlign.Center,
                            color = CustomBlue
                        )
                    }

                    // Twitter share button
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = {
                                shareViaTwitter(context, title)
                            })
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_share_24),
                            contentDescription = "Share via Twitter",
                            tint = CustomBlue,
                            modifier = Modifier
                                .size(52.dp)
                        )
                        Text(
                            text = "Twitter",
                            textAlign = TextAlign.Center,
                            color = CustomBlue
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cancel sharing
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onEvent(BucketListEvent.HideDialogForSharingItem)
                    }) {
                        Text(
                            text = "Cancel",
                            color = CustomRed,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

// Function to share completed goal via SMS, user will need to enter recipient by themselves after redirect
fun shareViaSMS(context: Context, title: String) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:")
            putExtra(
                "sms_body",
                "Hello!\n\nI completed my goal titled '$title' in the TickItOff bucket list app!" +
                        "\nIf you haven't already, you should try the app too!" +
                        "\n\nBest regards"
            )
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("SMS_SENDER", "Failed to send SMS: ${e.message}")
    }
}

// Function to share completed goal via email, user will need to enter recipient by themselves after redirect
fun shareViaEmail(context: Context, title: String) {
    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "I completed a goal in the TickItOff app") // ämne
        putExtra(
            Intent.EXTRA_TEXT,
            "Hello!\n\nI completed my goal titled '$title' in the TickItOff bucket list app!" +
                    "\nIf you haven't already, you should try the app too!" +
                    "\n\nBest regards"
        ) // Message
    }
    // Start the intent
    context.startActivity(Intent.createChooser(emailIntent, "Choose your client"))
}

// Function to share completed goal via twitter
fun shareViaTwitter(context: Context, title: String) {
    // Check that user is connected to internet for this
    if (isConnectedToInternet(context)) {
        val message =
            "Hello! I just completed my goal '$title' in the TickItOff app, you should try the app too!"
        val tweetUrl = "https://twitter.com/intent/tweet?text=${Uri.encode(message)}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl))
        context.startActivity(intent)
    } else {
        Toast.makeText(
            context,
            "You need to be connected to the internet to do this, try again later.",
            Toast.LENGTH_LONG
        ).show()
    }
}