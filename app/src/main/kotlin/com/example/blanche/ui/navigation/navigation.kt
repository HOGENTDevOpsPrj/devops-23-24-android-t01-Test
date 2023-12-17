package com.example.blanche.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.blanche.ui.HomeScreen
import com.example.blanche.ui.formulas.FormulaOverview
import com.example.blanche.ui.reservations.CalendarView

@Composable
fun navComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    fabActionVisible: Boolean = false,
    fabResetAction: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = NavigationOverview.Start.name,
        modifier = modifier,
    ) {
        composable(route = NavigationOverview.Start.name) {
            Log.i("vm inspection", "Nav to home screen")
            HomeScreen(goToReservations = {}, goToFormulas = {})
        }
        composable(route = NavigationOverview.Reservations.name) {
            Log.i("vm inspection", "Nav to reservations")
            CalendarView()
        }
        composable(route = NavigationOverview.Formulas.name) {
            FormulaOverview(isAddingVisible = fabActionVisible, makeInvisible = fabResetAction)
        }
    }
}
