package com.boukhari.orangebank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.boukhari.orangebank.ui.theme.OrangeBankTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OrangeBankTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                   // contentColor = MaterialTheme.colors.onSurface
                ) {

                    HelloWorld("Orange Bank")
                }
            }
        }
    }


    @Composable
    fun HelloWorld(text: String) {
        Box(Modifier.fillMaxSize()) {
            Text(text = text, modifier = Modifier.align(Alignment.Center))
        }
    }
}