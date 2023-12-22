package com.example.blanche.ui.reservations

import android.icu.util.Calendar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.blanche.R
import com.example.blanche.model.Reservation
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(reservation: Reservation) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    val startDate = Calendar.getInstance()
    startDate.set(
        LocalDate.parse(reservation?.startDate?.take(10)).year,
        LocalDate.parse(reservation?.startDate?.take(10)).monthValue - 1,
        LocalDate.parse(reservation?.startDate?.take(10)).dayOfYear
    )
    val endDate = Calendar.getInstance()
    endDate.set(
        LocalDate.parse(reservation?.endDate?.take(10)).year,
        LocalDate.parse(reservation?.endDate?.take(10)).monthValue - 1,
        LocalDate.parse(reservation?.endDate?.take(10)).dayOfYear
    )
    var datePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = startDate.timeInMillis,
        initialSelectedEndDateMillis = endDate.timeInMillis
    )
    var selectedDate by remember {
        mutableLongStateOf(startDate.timeInMillis)
    }
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)

    if (!showDatePicker) {
        OutlinedTextField(
            value = "${
                LocalDate.parse(reservation?.startDate?.take(10)).format(
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")
                )
            } tot " +
                    "${
                        LocalDate.parse(reservation?.endDate?.take(10))
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    }",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.periode)) },
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
                    //selectedDateRange = datePickerState.selectedDateMillis!!
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
            modifier = Modifier.fillMaxWidth()
        ) {
            DateRangePicker(
                state = datePickerState,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onPrimary,
                    dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.outlineVariant
                ),
                modifier = Modifier.height(500.dp).padding(top = 24.dp, bottom = 24.dp)
            )
        }
    }
}