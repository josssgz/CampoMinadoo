package com.example.campominadoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.campominadoo.ui.navigation.AppNavigation
import com.example.campominadoo.ui.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appInstance = applicationContext as MyApplication
        val factory = ViewModelFactory(appInstance.repository)

        setContent {
            Surface (
                modifier = Modifier
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavigation(factory = factory)
            }
        }
    }
}