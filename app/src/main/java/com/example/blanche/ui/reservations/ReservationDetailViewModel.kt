package com.example.blanche.ui.reservations

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.blanche.BlancheAdminApplication
import com.example.blanche.data.ReservationRepository
import com.example.blanche.model.Reservation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class ReservationDetailViewModel(
    private val reservationRepository: ReservationRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReservationDetailState())
    val uiState: StateFlow<ReservationDetailState> = _uiState.asStateFlow()

    private val reservationId: String = savedStateHandle["reservationId"]!!

    lateinit var uiItemsListState: StateFlow<ItemsListState>

    init {
        getReservationItems(reservationId)
    }
    fun showDropDown() {
        _uiState.update { it.copy(showFormulaDropDown = true) }
    }

    fun dismissDropDown() {
        _uiState.update { it.copy(showFormulaDropDown = false) }
    }

    fun updateReservation(reservation: Reservation){
        _uiState.update { it.copy(reservation = reservation) }
        viewModelScope.launch {
            reservationRepository.updateReservation(reservation)
        }
    }

    fun getReservationItems(reservationId: String) {
        try {
            viewModelScope.launch { reservationRepository.refresh() }

            uiItemsListState = reservationRepository.getReservationsByState(0).map { it ->
                ItemsListState(it.find { it.id == reservationId }?.items!!)
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(1_000L),
                    initialValue = ItemsListState()
                )
        }
        catch (e: IOException) {
            Log.i("Error", "$e")
        }
    }

    companion object {
        private var Instance : ReservationDetailViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BlancheAdminApplication)
                    val savedStateHandle = createSavedStateHandle()
                    val reservationRepository = application.container.reservationRepository
                    Instance = ReservationDetailViewModel(reservationRepository = reservationRepository, savedStateHandle = savedStateHandle)
                }
                Instance!!
            }
        }
    }
}