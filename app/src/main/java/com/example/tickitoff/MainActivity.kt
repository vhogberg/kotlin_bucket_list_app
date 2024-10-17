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
import com.example.tickitoff.ui.theme.CustomBeige
import com.example.tickitoff.ui.theme.TickItOffTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            BucketListDatabase::class.java,
            "bucketlistitems.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

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
                    val state by viewModel.state.collectAsState()
                    TickItOffPage(state = state, onEvent = viewModel::onEvent)
                }
            }
        }
    }
}