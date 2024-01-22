package com.example.blanche.ui.reservations

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun ReservationListScreen(
    modifier: Modifier = Modifier,
    reservationListViewModel: ReservationListViewModel = viewModel(factory = ReservationListViewModel.Factory),
    navController: NavController,
) {
    val reservationOverviewState by reservationListViewModel.uiState.collectAsState()
    val reservationListState by reservationListViewModel.uiListState.collectAsState()

    val reservationApiState = reservationListViewModel.reservationApiState

    Box(modifier = modifier) {
        when (reservationApiState) {
            is ReservationApiState.Loading -> Text("Aan het laden...")
            is ReservationApiState.Error -> Text("Kon niet laden...")
            is ReservationApiState.Success -> {
                ReservationListComponent(
                    reservationListState = reservationListState,
                    reservationOverviewState = reservationOverviewState,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun ReservationListComponent(
    reservationListState: ReservationListState,
    reservationOverviewState: ReservationOverviewState,
    navController: NavController,
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.padding(16.dp),
    ) {
        items(reservationListState.reservationList, key = { i -> i.id }) {
            ReservationView(
                reservation = it,
                goToReservationDetails = {
                    navController.navigate("ReservationDetails/${it.id}")
                }
            )
        }
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(reservationOverviewState.scrollActionIdx) {
        if (reservationOverviewState.scrollActionIdx != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(reservationOverviewState.scrollToItemIndex)
            }
        }
    }
}