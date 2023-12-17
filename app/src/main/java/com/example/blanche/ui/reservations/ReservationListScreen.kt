package com.example.blanche.ui.reservations

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ReservationListScreen(
    modifier: Modifier = Modifier,
    reservationListViewModel: ReservationListViewModel = viewModel(factory = ReservationListViewModel.Factory),
) {
    val reservationOverviewState by reservationListViewModel.uiState.collectAsState()
    val reservationListState by reservationListViewModel.uiListState.collectAsState()

    val reservationApiState = reservationListViewModel.reservationApiState

    Box(modifier = modifier) {
        when (reservationApiState) {
            is ReservationApiState.Loading -> Text("Aan het laden...")
            is ReservationApiState.Error -> Text("Kon niet laden...")
            is ReservationApiState.Success -> ReservationListComponent(
                reservationListState = reservationListState,
                reservationOverviewState = reservationOverviewState,
            )
        }
    }
}

@Composable
fun ReservationListComponent(
    modifier: Modifier = Modifier,
    reservationListState: ReservationListState,
    reservationOverviewState: ReservationOverviewState
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = Modifier.padding(16.dp)
    ) {
        Log.i("test", "${reservationListState.reservationList}")
        items(reservationListState.reservationList) {
            ReservationView(reservation = it)
        }
    }
}