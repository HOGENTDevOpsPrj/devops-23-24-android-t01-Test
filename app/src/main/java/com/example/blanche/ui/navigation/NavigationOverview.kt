package com.example.blanche.ui.navigation
// FormulaScreen.kt

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.blanche.R

enum class NavigationOverview(@StringRes val title: Int, val icon: ImageVector) {
    Start(title = R.string.app_name, icon = Icons.Filled.Home),
    Reservations(title = R.string.Reservations, icon = Icons.Filled.CalendarMonth),
    ReservationDetails(title = R.string.ReservationDetails, icon = Icons.Filled.List),
    Extras(title = R.string.Extras, icon = Icons.Filled.AddCircle),
    Formulas(title = R.string.Formulas, icon = Icons.Filled.ListAlt)
}
