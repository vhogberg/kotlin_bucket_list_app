package com.example.tickitoff

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.tickitoff.data.BucketListItem
import com.example.tickitoff.ui.screens.TickItOffPage
import com.example.tickitoff.ui.theme.CustomBeige
import com.example.tickitoff.ui.theme.TickItOffTheme
import com.example.tickitoff.utils.scheduleRecurringReminder
import com.example.tickitoff.viewmodel.BucketListItemViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            // insertTestData()
        } else {
            // If not granted, launch request to grant
            requestPermissions()
        }
    }

    private fun setMainView() {
        enableEdgeToEdge()
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

    // Method to insert test data
    private fun insertTestData() {
        CoroutineScope(Dispatchers.IO).launch {
            val testItems = getTestBucketListItems()
            testItems.forEach { item ->
                db.dao.upsertItem(item)
            }
        }
    }
}

// Input data for development
fun getTestBucketListItems(): List<BucketListItem> {
    return listOf(
        BucketListItem(
            title = "Go to Paris",
            description = "I need to save up at least 5000 dollars and find someone to travel with",
            doneByYear = 2025,
            completed = false
        ),
        BucketListItem(
            title = "Learn to play the guitar",
            description = "Need to buy a guitar and find a good online course or local teacher",
            doneByYear = 2024,
            completed = false
        ),
        BucketListItem(
            title = "Run a marathon",
            description = "Start a training regimen and sign up for a local marathon event",
            doneByYear = 2026,
            completed = false
        ),
        BucketListItem(
            title = "Write a novel",
            description = "Develop plot and characters, set aside time each day to write",
            doneByYear = 2027,
            completed = false
        ),
        BucketListItem(
            title = "Scuba dive in the Great Barrier Reef",
            description = "Get scuba certified and plan a trip to Australia",
            doneByYear = 2028,
            completed = false
        ),
        BucketListItem(
            title = "Start a small business",
            description = "Develop a business plan, secure funding, and register the company",
            doneByYear = 2026,
            completed = false
        ),
        BucketListItem(
            title = "Climb Mount Kilimanjaro",
            description = "Train for high-altitude hiking and book a guided expedition",
            doneByYear = 2027,
            completed = true
        ),
        BucketListItem(
            title = "Learn to speak fluent Spanish",
            description = "Enroll in language classes and plan an immersion trip to Spain",
            doneByYear = 2025,
            completed = true
        ),
        BucketListItem(
            title = "Complete a cross-country road trip",
            description = "Plan route, save for expenses, and prepare vehicle for long journey",
            doneByYear = 2024,
            completed = true
        )
    )
}
