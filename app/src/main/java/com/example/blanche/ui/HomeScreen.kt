package com.example.blanche.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blanche.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 12.dp)
                .size(width = 180.dp, height = 150.dp)
        ) {
            val image = painterResource(R.drawable.reservations)
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    //colorFilter = ColorFilter.tint(color = Color.DarkGray, blendMode = BlendMode.Softlight)
                )
                Text(
                    text = "Reservaties",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 45.sp,
                )
            }
        }
        ElevatedCard(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 12.dp)
                .size(width = 180.dp, height = 150.dp)
        ) {
            val image = painterResource(R.drawable.extras)
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    //colorFilter = ColorFilter.tint(color = Color.DarkGray, blendMode = BlendMode.Softlight)
                )
                Text(
                    text = "Materiaal",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 45.sp,
                )
            }
        }
        ElevatedCard(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 12.dp)
                .size(width = 180.dp, height = 150.dp)
        ) {
            val image = painterResource(R.drawable.formulas)
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    //colorFilter = ColorFilter.tint(color = Color.DarkGray, blendMode = BlendMode.Softlight)
                )
                Text(
                    text = "Formules",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 45.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomeScreen("Blanche")
}
