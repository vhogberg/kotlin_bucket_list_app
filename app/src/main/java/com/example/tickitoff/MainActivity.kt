package com.example.tickitoff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.tickitoff.data.BucketListDatabase
import com.example.tickitoff.ui.screens.TickItOffPage
import com.example.tickitoff.ui.theme.CustomBeige
import com.example.tickitoff.ui.theme.TickItOffTheme
import com.example.tickitoff.viewmodel.BucketListItemViewModel

// Main start activity of app

class MainActivity : ComponentActivity() {

    // Build the db
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            BucketListDatabase::class.java,
            "bucketlistitems.db"
        ).fallbackToDestructiveMigration() // forcibly removes old when manually creating new version of db
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            TickItOffTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CustomBeige
                ) {
                    val state by viewModel.state.collectAsState() // get state
                    TickItOffPage(state = state, onEvent = viewModel::onEvent) // initialize main page with state and viewmodel
                }
            }
        }
    }
}