package com.example.blanche.ui.formulas

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.blanche.R
import com.example.blanche.model.Formula

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaItem(
    modifier: Modifier = Modifier,
    formula: Formula,
    viewModel: FormulaOverviewViewModel,
) {
    ElevatedCard {
        var expanded by rememberSaveable { mutableStateOf(false) }
        val color by animateColorAsState(
            targetValue = if (expanded) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onPrimary
            },
            label = "colorAnimation",
        )
        Row(
            modifier = modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium,
                    ),
                )
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .background(color)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                Row {
                    Text(
                        text = formula.name,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.titleLarge,
                        textDecoration = TextDecoration.None,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            viewModel.toggleEditFormulaScreen()
                            viewModel.setFormula(formula) },
                        modifier = Modifier.align(Alignment.CenterVertically),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            contentDescription = stringResource(R.string.edit_formula_content_description),
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.deleteFormula(formula)
                        },
                        modifier = Modifier.align(Alignment.CenterVertically),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            contentDescription = stringResource(R.string.delete_formula_content_description),
                        )
                    }
                    FormulaItemButton(
                        expanded = expanded,
                        onClick = { expanded = !expanded },
                        modifier = Modifier.align(Alignment.Top),
                    )
                }
                if (expanded) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = formula.description,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(Modifier.height(4.dp))
                    formula.pricePerDays.forEach { e ->
                        if (e.key == 1)
                            Text(
                                text = "${e.key} dag: ${e.value} €",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        else
                            Text(
                                text = "${e.key} dagen: ${e.value} €",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Prijs per extra dag: ${formula.pricePerExtraDay} €",
                        style = MaterialTheme.typography.bodyLarge,
                    )
/*                    Text(
                        text = formula.imageUrl,
                        style = MaterialTheme.typography.bodyMedium,
                    )*/
                }
            }
        }
    }
}

@Composable
fun FormulaItemButton(expanded: Boolean, onClick: () -> Unit, modifier: Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun EditButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        Text("Save")
    }
}
