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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationView(
    modifier: Modifier = Modifier,
    reservation: Reservation
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary),
        onClick = { /*TODO*/ }
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
                .height(80.dp)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(text = "${reservation.formula.name}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${reservation.numberOfPersons} personen")
            }
            Text(text = "${reservation.startDate.take(10)} - ${reservation.endDate.take(10)}")
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
