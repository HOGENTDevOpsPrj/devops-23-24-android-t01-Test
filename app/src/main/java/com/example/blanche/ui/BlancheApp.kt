package com.example.blanche.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.blanche.R
import com.example.blanche.ui.components.BlancheNavigationRail
import com.example.blanche.ui.components.NavigationDrawerContent
import com.example.blanche.ui.formulas.FormulaOverviewScreen
import com.example.blanche.ui.navigation.BlancheAppBottomBar
import com.example.blanche.ui.navigation.BlancheAppTopBar
import com.example.blanche.ui.navigation.navComponent
import com.example.blanche.ui.themes.BlancheTheme
import com.example.blanche.ui.util.BlancheNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheApp(navigationType: BlancheNavigationType,
            navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }
    val goHome: () -> Unit = {
        navController.popBackStack(
            FormulaOverviewScreen.Start.name,
            inclusive = false,
        )
    }
    val currentScreenTitle = FormulaOverviewScreen.valueOf(
        backStackEntry?.destination?.route ?: FormulaOverviewScreen.Start.name,
    ).title

    var isAddNewVisible by remember{mutableStateOf(false)}

    //Only use scaffold in compact mode
    if(navigationType == BlancheNavigationType.PERMANENT_NAVIGATION_DRAWER){
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {

                NavigationDrawerContent(
                    selectedDestination = navController.currentDestination,
                    onTabPressed = {node: String -> navController.navigate(node)},
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .padding(dimensionResource(R.dimen.drawer_padding_content))
                )
            }
        }){

            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    BlancheAppTopBar(
                        canNavigateBack = canNavigateBack,
                        navigateUp = navigateUp,
                        currentScreenTitle = currentScreenTitle,
                    )
                },
                floatingActionButton = {

                    FloatingActionButton(onClick = { isAddNewVisible = !isAddNewVisible}) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },
                //modifier = Modifier.padding(dimensionResource(id = R.dimen.drawer_width), 0.dp, 0.dp, 0.dp )
            ) { innerPadding ->

                navComponent(navController = navController, modifier = Modifier.padding(innerPadding),
                    fabActionVisible = isAddNewVisible, fabResetAction = {isAddNewVisible = false})
            }
        }
    }
    else if(navigationType == BlancheNavigationType.BOTTOM_NAVIGATION) {
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

                BlancheAppBottomBar(goHome)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { isAddNewVisible = !isAddNewVisible }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            },
        ) { innerPadding ->

            navComponent(navController, modifier = Modifier.padding(innerPadding))
        }
    }
    else{
        Row {
            AnimatedVisibility(visible = navigationType == BlancheNavigationType.NAVIGATION_RAIL) {
                val navigationRailContentDescription = stringResource(R.string.navigation_rail)
                BlancheNavigationRail(
                    selectedDestination = navController.currentDestination,
                    onTabPressed = { node: String -> navController.navigate(node) },
                )
            }
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    BlancheAppTopBar(
                        canNavigateBack = canNavigateBack,
                        navigateUp = navigateUp,
                        currentScreenTitle = currentScreenTitle,
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { isAddNewVisible = !isAddNewVisible }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },
            ) { innerPadding ->

                navComponent(navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }



}


@Preview(showBackground = true, widthDp = 500)
@Composable
fun BlancheAppPreview() {
    BlancheTheme {
        // create a box to overlap image and texts
        Surface(modifier = Modifier.fillMaxWidth()) {
            BlancheApp(BlancheNavigationType.BOTTOM_NAVIGATION)
        }
    }
}
