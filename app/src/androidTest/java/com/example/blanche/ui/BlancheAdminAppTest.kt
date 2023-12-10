package com.example.blanche.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.blanche.ui.util.BlancheNavigationType
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val NAVIGATE_TO_RESERVATIONS_PAGE = "navigate to reservations page"

class BlancheAdminAppTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun initializeApp() {
        composeTestRule.setContent {
            navController = TestNavHostController((LocalContext.current))
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            BlancheApp(navigationType = BlancheNavigationType.BOTTOM_NAVIGATION, navController)
        }
    }

    @Test
    fun startScreenShowsReservationsButton() {
        composeTestRule
            .onNodeWithContentDescription(NAVIGATE_TO_RESERVATIONS_PAGE)
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun clickOnReservationsNavigatesToReservationsOverviewScreen() {
        composeTestRule
            .onNodeWithContentDescription(NAVIGATE_TO_RESERVATIONS_PAGE)
            .performClick()
        assertEquals("", navController.currentBackStackEntry?.destination?.route)
    }

    private fun getResourceString(@StringRes key: Int): String {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        return context.resources.getString(key)
    }
}