package com.example.blanche.ui.reservations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.blanche.R
import com.example.blanche.ui.formulas.FormulaOverviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationDetailScreen(
    reservationId: String,
    navController: NavController,
    reservationListViewModel: ReservationListViewModel = viewModel(factory = ReservationListViewModel.Factory),
    reservationDetailViewModel: ReservationDetailViewModel = viewModel(factory = ReservationDetailViewModel.Factory),
    formulaOverviewViewModel: FormulaOverviewViewModel = viewModel(factory = FormulaOverviewViewModel.Factory),
) {
    val reservationListState by reservationListViewModel.uiListState.collectAsStateWithLifecycle()
    val reservation = reservationListState.reservationList.find { reservation -> reservation.id == reservationId }
    val formulaListState by formulaOverviewViewModel.uiListState.collectAsState()
    val formulas = formulaListState.formulaList

    val extraItems = remember {reservation?.items?.toMutableStateList()}
    val reservationDetailUiState by reservationDetailViewModel.uiState.collectAsStateWithLifecycle()

    var selectedFormula by remember { mutableStateOf(formulas.find { f -> f.name ==  reservation?.formula?.name}) }
    var numberOfPersons by remember { mutableStateOf(reservation?.numberOfPersons.toString()) }
    var beerType by remember { mutableStateOf(reservation?.typeOfBeer?.name ?: "") }
    var totalPrice by remember { mutableStateOf(reservation?.totalPrice.toString()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        ExposedDropdownMenuBox(
            expanded = reservationDetailUiState.showFormulaDropDown,
            onExpandedChange = { reservationDetailViewModel.showDropDown() },
        ) {
            OutlinedTextField(
                value = "${selectedFormula?.name ?: reservation?.formula?.name}",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = reservationDetailUiState.showFormulaDropDown) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                label = { Text(stringResource(R.string.formule)) },
            )
            ExposedDropdownMenu(
                expanded = reservationDetailUiState.showFormulaDropDown,
                onDismissRequest = { reservationDetailViewModel.showDropDown() },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.onPrimary)
            ) {
                formulas.forEach { formula ->
                    DropdownMenuItem(
                        text = { Text(formula.name) },
                        onClick = {
                            selectedFormula = formula
                            reservation?.formula = formula
                            reservationDetailViewModel.setReservation(reservation!!)
                            reservationDetailViewModel.dismissDropDown()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if ((reservation?.startDate == reservation?.endDate) && reservation != null) {
            DatePicker(
                reservation = reservation
            )
        } else if (reservation != null) {
            DateRangePicker(
                reservation = reservation
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = numberOfPersons,
            onValueChange = {
                numberOfPersons = it
                reservation?.numberOfPersons = it.toIntOrNull() ?: 0
            },
            label = { Text(stringResource(R.string.aantal_personen)) },
            modifier = Modifier.fillMaxWidth()
        )
        if (selectedFormula?.hasDrinks == true) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = beerType,
                onValueChange = {
                    beerType = it
                    reservation?.typeOfBeer?.name = it
                },
                label = { Text(stringResource(R.string.type_bier)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = totalPrice,
            onValueChange = {
                totalPrice = it
                reservation?.totalPrice = it.toDouble()
            },
            label = { Text(stringResource(R.string.totaalprijs)) },
            modifier = Modifier.fillMaxWidth()
        )
        if(reservation?.notes != null) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "${reservation.notes}",
                onValueChange = {},
                label = { Text(stringResource(R.string.opmerkingen)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (extraItems != null && !extraItems.isEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Extra materiaal", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Column() {
                extraItems.forEach{
                    InputChip(
                        selected = false,
                        onClick = {  },
                        label = { Text(
                            text = "${it.quantity} x ${it.product.name}",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp)
                        ) },
                        colors = InputChipDefaults.inputChipColors(MaterialTheme.colorScheme.outlineVariant),
                        border = InputChipDefaults.inputChipBorder(borderColor = MaterialTheme.colorScheme.outlineVariant),
                        trailingIcon = {
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                extraItems.remove(it)
                                reservation?.items = extraItems
                                reservationDetailViewModel.setReservation(reservation!!)
                            }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Localized description",
                                    Modifier.size(InputChipDefaults.IconSize),
                                    tint = Color.White,
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Klantgegevens", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedCard(
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(Color.Transparent),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text("${reservation?.customer?.firstName} ${reservation?.customer?.lastName}")
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text( "${reservation?.customer?.customerAddress?.street}")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${reservation?.customer?.customerAddress?.number}")
                }
                Row {
                    Text("${reservation?.customer?.customerAddress?.postalCode}")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${reservation?.customer?.customerAddress?.city}")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("${reservation?.customer?.customerAddress?.country}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("${reservation?.customer?.email?.value}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("${reservation?.customer?.phoneNumber}")
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            ElevatedButton(
                onClick = {
                    reservation?.state = 1
                    reservationDetailViewModel.setReservation(reservation!!)
                    navController.navigate("reservations")
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Bevestig offerte")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}
