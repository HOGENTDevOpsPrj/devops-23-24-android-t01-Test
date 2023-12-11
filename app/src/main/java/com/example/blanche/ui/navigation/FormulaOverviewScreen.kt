package com.example.blanche.ui.navigation
// FormulaScreen.kt

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.blanche.R

enum class FormulaOverviewScreen(@StringRes val title: Int, val icon: ImageVector) {
    Start(title = R.string.app_name, icon = Icons.Filled.Home),
    Reservations(title = R.string.Reservations, icon = Icons.Filled.CalendarMonth)
}
