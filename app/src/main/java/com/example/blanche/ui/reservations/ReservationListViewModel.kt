package com.example.blanche.ui.reservations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.blanche.BlancheAdminApplication
import com.example.blanche.data.ReservationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException

class ReservationListViewModel(
    private val reservationRepository: ReservationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReservationOverviewState())
    val uiState: StateFlow<ReservationOverviewState> = _uiState.asStateFlow()

    lateinit var uiListState: StateFlow<ReservationListState>

    var reservationApiState: ReservationApiState by mutableStateOf(ReservationApiState.Loading)
        private set

    init {
        getRepoReservations()
    }

    private fun getRepoReservations()  {
        try {
            viewModelScope.launch { reservationRepository.refresh() }

            uiListState = reservationRepository.getReservationsByState(state = 5).map { ReservationListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(1_000L),
                    initialValue = ReservationListState()
                )
            reservationApiState = ReservationApiState.Success
        }
        catch (e: IOException) {
            reservationApiState = ReservationApiState.Error
        }
    }

    companion object {
        private var Instance : ReservationListViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BlancheAdminApplication)
                    val reservationRepository = application.container.reservationRepository
                    Instance = ReservationListViewModel(reservationRepository = reservationRepository)
                }
                Instance!!
            }
        }
    }
}
