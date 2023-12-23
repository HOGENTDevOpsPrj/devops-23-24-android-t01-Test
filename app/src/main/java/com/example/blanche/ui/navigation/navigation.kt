package com.example.blanche.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.blanche.ui.HomeScreen
import com.example.blanche.ui.formulas.FormulaOverview
import com.example.blanche.ui.products.ProductOverview
import com.example.blanche.ui.reservations.ReservationDetailScreen
import com.example.blanche.ui.reservations.ReservationListScreen

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
            HomeScreen(goToReservations = {}, goToFormulas = {}, goToProduct = {})
        }
        composable(route = NavigationOverview.Reservations.name) {
            Log.i("vm inspection", "Nav to reservations")
            ReservationListScreen(navController = navController)
        }
        composable("ReservationDetails/{reservationId}", arguments = listOf(
            navArgument("reservationId") { type = NavType.StringType }
        )) { it ->
            Log.i("vm inspection", "Nav to reservationdetail")
            ReservationDetailScreen(it.arguments?.getString("reservationId")!!, navController = navController)
        }
        composable(route = NavigationOverview.Formulas.name) {
            FormulaOverview()
        }
        composable(route = NavigationOverview.Extras.name){
            ProductOverview(isAddingVisible = fabActionVisible, makeInvisible = fabResetAction)
        }
    }
}
