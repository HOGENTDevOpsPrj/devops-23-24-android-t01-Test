package com.example.blanche.ui.reservations

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CalendarView() {
    Row {
        val calendar = Calendar.getInstance()
        val year = 2023
        val month = 11
        val day = 16
        calendar.set(year, month, day)
        val occupiedDate = calendar.timeInMillis
        val state = rememberDateRangePickerState(
            //initialSelectedStartDateMillis = occupiedDate,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return (utcTimeMillis > (occupiedDate + (24 * 60 * 60 * 1000))) || (utcTimeMillis < occupiedDate)
                }
            }
        )
        DateRangePicker(
            state = state,
            colors = DatePickerDefaults.colors(
                todayContentColor = MaterialTheme.colorScheme.outlineVariant,
                todayDateBorderColor = MaterialTheme.colorScheme.outlineVariant,
                disabledDayContentColor = MaterialTheme.colorScheme.error,
                //dayInSelectionRangeContainerColor = Color.Red
            ),
            modifier = Modifier.weight(1f))
    }
}
