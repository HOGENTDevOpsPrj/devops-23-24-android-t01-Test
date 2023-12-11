package com.example.blanche

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.blanche.ui.BlancheApp
import com.example.blanche.ui.themes.BlancheTheme
import com.example.blanche.ui.util.BlancheNavigationType

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlancheTheme {
                val image = painterResource(R.drawable.backgroundimage)
                // create a Surface to overlap image and texts
                Surface (modifier = Modifier.fillMaxWidth()){
                    val windowSize = calculateWindowSizeClass(activity = this)
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        alpha = 0.2F
                    )
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            BlancheApp(BlancheNavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            BlancheApp(BlancheNavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            BlancheApp(navigationType = BlancheNavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            BlancheApp(navigationType = BlancheNavigationType.BOTTOM_NAVIGATION)
                        }
                    }
                }
            }
        }
    }
}
