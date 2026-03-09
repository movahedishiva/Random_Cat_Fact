package com.example.randomcatfact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.randomcatfact.presentation.ui.screen.CatFactScreen
import com.example.randomcatfact.presentation.ui.theme.RandomCatFactTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomCatFactTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    CatFactScreen( modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
