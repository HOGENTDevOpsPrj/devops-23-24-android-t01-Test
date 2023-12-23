package com.example.blanche.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blanche.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(goToReservations: () -> Unit, goToFormulas: () -> Unit, goToProduct: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.logoblanchezwart),
        contentDescription = null,
        modifier = Modifier.size(115.dp).padding(horizontal = 0.dp, vertical = 20.dp)
    )
    Column(
        modifier = Modifier.fillMaxHeight().padding(horizontal = 0.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        ElevatedCard(
            onClick = goToReservations,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 12.dp)
                .size(width = 180.dp, height = 160.dp)
        ) {
            val image = painterResource(R.drawable.reservations)
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(color = Color.DarkGray, blendMode = BlendMode.Softlight)
                )
                Text(
                    text = "Reservaties",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 38.sp,
                )
            }
        }
        ElevatedCard(
            onClick = goToProduct,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 12.dp)
                .size(width = 180.dp, height = 160.dp)
        ) {
            val image = painterResource(R.drawable.extras)
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    colorFilter = ColorFilter.tint(color = Color.Gray, blendMode = BlendMode.Softlight)
                )
                Text(
                    text = "Materiaal",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 38.sp,
                )
            }
        }
        ElevatedCard(
            onClick = goToFormulas,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 12.dp)
                .size(width = 180.dp, height = 160.dp)
        ) {
            val image = painterResource(R.drawable.formulas)
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    colorFilter = ColorFilter.tint(color = Color.Gray, blendMode = BlendMode.Softlight)
                )
                Text(
                    text = "Formules",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 38.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomeScreen(goToReservations = {}, goToFormulas = {}, goToProduct = {})
}
