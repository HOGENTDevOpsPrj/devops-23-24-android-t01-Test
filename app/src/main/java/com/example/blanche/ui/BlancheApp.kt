package com.example.blanche.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.blanche.ui.formulas.FormulaOverview
import com.example.blanche.ui.formulas.FormulaOverviewScreen
import com.example.blanche.ui.navigation.BlancheAppBottomBar
import com.example.blanche.ui.navigation.BlancheAppTopBar
import com.example.blanche.ui.themes.BlancheTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheApp(navController: NavHostController = rememberNavController()) {
    var addingVisible by rememberSaveable { mutableStateOf(false) }
    val backStackEntry by navController.currentBackStackEntryAsState()

    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }
    val goHome: () -> Unit =
        { navController.popBackStack(FormulaOverviewScreen.Start.name, inclusive = false) }
    val goToAbout = { navController.navigate(FormulaOverviewScreen.About.name) }

    val currentScreenTitle = FormulaOverviewScreen.valueOf(
        backStackEntry?.destination?.route ?: FormulaOverviewScreen.Start.name,
    ).title

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            BlancheAppTopBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                currentScreenTitle = currentScreenTitle,
            )
        },
        bottomBar = {
            BlancheAppBottomBar(goHome, goToAbout)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { addingVisible = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FormulaOverviewScreen.Start.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = FormulaOverviewScreen.Start.name) {
                // refactor: move the task overview to a separate composable
                // note: making the tasks clickable will be for the next lesson!
                // it requires a viewModel 🤩
                FormulaOverview(
                    addingVisible = addingVisible,
                    { visible -> addingVisible = visible },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BlancheAppPreview() {
    BlancheTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            BlancheApp(
                navController = rememberNavController(),
            )
        }
    }
}
