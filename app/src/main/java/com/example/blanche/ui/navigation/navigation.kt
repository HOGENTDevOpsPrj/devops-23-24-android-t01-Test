package com.example.blanche.ui.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.blanche.ui.formulas.FormulaOverview
import com.example.blanche.ui.formulas.FormulaOverviewScreen

@Composable
fun navComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    fabActionVisible: Boolean = false,
    fabResetAction: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = FormulaOverviewScreen.Start.name,
        modifier = modifier,
    ) {
        composable(route = FormulaOverviewScreen.Start.name) {
            Log.i("vm inspection", "Nav to TaskOverview")
            FormulaOverview(isAddingVisible = fabActionVisible, makeInvisible = fabResetAction)
        }
        composable(route = FormulaOverviewScreen.Detail.name) {
            Log.i("vm inspection", "Nav to detail")
            Text("Detail")
        }
    }
}
