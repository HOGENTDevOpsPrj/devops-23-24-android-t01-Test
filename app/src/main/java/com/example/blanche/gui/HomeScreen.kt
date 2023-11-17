package com.example.blanche.gui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blanche.R

@Composable
fun HomePage(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Blanche",
            fontFamily = FontFamily(Font(R.font.millenia)),
            fontSize = 85.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier.padding(top = 80.dp),
        )
        Text(
            text = "De mobiele tapinstallatie voor al je evenementen",
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier.padding(horizontal = 50.dp),
        )
        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(id = R.drawable.blanchelogo),
            contentDescription = "FoodTruckLogo",
        )
        Button(
            onClick = {},
            modifier = Modifier.padding(top = 40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFBD6B39)),
        ) {
            Text(
                text = "Stel je offerte samen",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(5.dp),
            )
        }
        Button(
            onClick = {},
            modifier = Modifier.padding(top = 20.dp).width(250.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFBD6B39)),
        ) {
            Text(
                text = "Beschikbaarheid",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage("Blanche")
}
