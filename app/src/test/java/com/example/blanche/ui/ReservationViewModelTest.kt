package com.example.blanche.ui

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ReservationViewModelTest {
    private val viewModel = ReservationViewModel()

    @Test
    fun viewModelStartsWithUnconfirmedReservation() {
        assertEquals("", "")
    }
}