package dev.jianastrero.compose_nav_transition.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.jianastrero.compose_nav_transition.example.screens.FirstScreen
import dev.jianastrero.compose_nav_transition.example.screens.SecondScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var screen by rememberSaveable { mutableIntStateOf(1) }

            when (screen) {
                1 -> FirstScreen(
                    onGotoSecondScreen = { screen = 2 },
                    modifier = Modifier.fillMaxSize()
                )
                2 -> SecondScreen(
                    onGotoFirstScreen = { screen = 1 },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
