package com.example.tickitoff

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.tickitoff.data.BucketListDatabase
import com.example.tickitoff.ui.screens.TickItOffPage
import com.example.tickitoff.ui.theme.CustomBeige
import com.example.tickitoff.ui.theme.TickItOffTheme
import com.example.tickitoff.utils.scheduleRecurringReminder
import com.example.tickitoff.viewmodel.BucketListItemViewModel

// Main start activity of app

class MainActivity : ComponentActivity() {

    // Build the db
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            BucketListDatabase::class.java,
            "bucketlistitems.db"
        )
            .fallbackToDestructiveMigration() // forcibly removes old when manually creating new version of db
            .build()
    }

    // Create the viewmodel for bucket list items/goals
    private val viewModel by viewModels<BucketListItemViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BucketListItemViewModel(db.dao) as T
                }
            }
        }
    )

    // To request permissions
    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                setMainView()
            }
        }

    // Method that returns a boolean saying whether permissions are granted or not
    private fun arePermissionsGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }

    // Method to request permissions
    private fun requestPermissions() {
        // POST_NOTIFICATIONS permission is only required to be asked like this after the TIRAMISU sdk
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // If not granted, launch request to grant
                permissionRequest.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check whether premissions are granted
        if (arePermissionsGranted()) {
            setMainView()
            scheduleRecurringReminder(this)
        } else {
            // If not granted, launch request to grant
            requestPermissions()
        }
    }

    private fun setMainView() {
        // enableEdgeToEdge()
        setContent {
            TickItOffTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CustomBeige
                ) {
                    val state by viewModel.state.collectAsState() // get state
                    TickItOffPage(
                        state = state,
                        onEvent = viewModel::onEvent
                    ) // initialize main page with state and viewmodel
                }
            }
        }
    }
}

