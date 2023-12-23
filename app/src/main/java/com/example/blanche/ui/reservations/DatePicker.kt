package com.example.blanche.ui.reservations

import android.icu.util.Calendar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.blanche.R
import com.example.blanche.model.Reservation
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    reservation: Reservation,
    reservationDetailViewModel: ReservationDetailViewModel = viewModel(factory = ReservationDetailViewModel.Factory),
) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    val calendar = Calendar.getInstance()
    calendar.set(
        LocalDate.parse(reservation?.startDate?.take(10)).year - 1,
        LocalDate.parse(reservation?.startDate?.take(10)).monthValue,
        LocalDate.parse(reservation?.startDate?.take(10)).dayOfYear
    )
    var datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis,)
    var selectedDate by remember {
        mutableLongStateOf(calendar.timeInMillis)
    }
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)
    val reformatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)

    if (!showDatePicker) {
        OutlinedTextField(
            value = "${formatter.format(Date(selectedDate))}",
            onValueChange = {

            },
            label = { Text(text = stringResource(R.string.datum)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        Icons.Filled.CalendarMonth,
                        contentDescription = "open date picker"
                    )
                }
            }
        )
    } else {
        DatePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    selectedDate = datePickerState.selectedDateMillis!!
                    println(reformatter.format(selectedDate))
                    reservation?.startDate = reformatter.format(selectedDate)
                    reservation?.endDate = reformatter.format(selectedDate)
                    reservationDetailViewModel.setReservation(reservation)
                }) {
                    Text(
                        text = "Bevestig",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(
                        text = "Annuleer",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.onPrimary
            ),
        ) {
            androidx.compose.material3.DatePicker(
                state = datePickerState,
                showModeToggle = false,
            )
        }
    }

}