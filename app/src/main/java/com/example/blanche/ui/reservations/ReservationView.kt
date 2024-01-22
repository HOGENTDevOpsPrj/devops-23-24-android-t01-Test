package com.example.blanche.ui.reservations

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blanche.model.Reservation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationView(
    modifier: Modifier = Modifier,
    reservation: Reservation,
    goToReservationDetails: () -> Unit
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
        onClick = { goToReservationDetails() }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium,
                    ),
                )
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(text = "${reservation.formula.name}")
                Spacer(modifier = Modifier.height(8.dp))
                if (reservation.startDate == reservation.endDate) {
                    Text(text = "${LocalDate.parse(reservation.startDate.take(10)).format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy"))}")
                } else {
                    Text(text = "${LocalDate.parse(reservation.startDate.take(10)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))} - ${LocalDate.parse(reservation.endDate.take(10)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}")
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "${reservation.numberOfPersons} personen")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${reservation.totalPrice} €")
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

/*@Preview
@Composable
private fun Reservation() {
    BlancheTheme {
        ReservationView()
    }
}*/
