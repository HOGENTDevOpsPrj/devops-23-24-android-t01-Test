package com.example.blanche

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.blanche.ui.BlancheApp
import com.example.blanche.ui.themes.BlancheTheme
import com.example.blanche.ui.util.BlancheNavigationType

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlancheTheme {
                // create a Surface to overlap image and texts
                Surface (modifier = Modifier.fillMaxWidth()){
                    val windowSize = calculateWindowSizeClass(activity = this)
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            BlancheApp(BlancheNavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            BlancheApp(BlancheNavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            BlancheApp(BlancheNavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            BlancheApp(BlancheNavigationType.BOTTOM_NAVIGATION)
                        }
                    }
                }
            }
        }
    }
}
