package com.example.findapic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.findapic.ui.MainScreen
import com.example.findapic.ui.theme.FindAPicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FindAPicTheme {
                MainScreen()
            }
        }
    }
}