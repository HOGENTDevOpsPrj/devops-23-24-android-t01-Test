package com.example.blanche.ui.reservations

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat

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
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return (utcTimeMillis > (occupiedDate + (24 * 60 * 60 * 1000))) || (utcTimeMillis < occupiedDate)
                }
            }
        )

        fun getFormattedDate(timeInMillis: Long): String{
            val calender = Calendar.getInstance()
            calender.timeInMillis = timeInMillis
            val dateFormat = SimpleDateFormat("dd MMM")
            return dateFormat.format(calender.timeInMillis)
        }

        DateRangePicker(
            state = state,
            colors = DatePickerDefaults.colors(
                todayContentColor = MaterialTheme.colorScheme.scrim,
                todayDateBorderColor = MaterialTheme.colorScheme.scrim,
                disabledDayContentColor = MaterialTheme.colorScheme.outlineVariant,
            ),
            title = { Text(text = "") },
            headline = {
                Row(modifier = Modifier
                    .padding(16.dp)) {
                    val calendarCurrent = Calendar.getInstance()
                    Box() {
                        (if(state.selectedStartDateMillis!=null) state.selectedStartDateMillis?.let { getFormattedDate(it) + " - " } else { getFormattedDate(calendarCurrent.timeInMillis) + " - " })?.let { Text(text = it) }
                    }
                    Box() {
                        (if(state.selectedEndDateMillis!=null) state.selectedEndDateMillis?.let { getFormattedDate(it) } else getFormattedDate(calendarCurrent.timeInMillis))?.let { Text(text = it) }
                    }
                }
            },
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp, vertical = 16.dp)
        )
    }
}
