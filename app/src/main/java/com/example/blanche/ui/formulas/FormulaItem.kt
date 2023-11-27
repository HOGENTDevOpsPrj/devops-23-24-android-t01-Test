package com.example.blanche.ui.formulas

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blanche.R

@Composable
fun FormulaItem(
    modifier: Modifier = Modifier,
    name: String = "",
    description: String = "",
    nrOfDays: Int = 0,
    price: Double = 0.0,
    imageUrl: String = "",
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small)),
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        val color by animateColorAsState(
            targetValue = if (expanded) {
                MaterialTheme.colorScheme.tertiaryContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            },
            label = "colorAnimation",
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium,
                    ),
                )
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .background(color),
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
            ) {
                Row {
                    Text(
                        text = name,
                        modifier = Modifier.padding(end = 8.dp).align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = TextDecoration.None,
                    )
                    Text(
                        text = "€$price",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = TextDecoration.None,
                    )
                }
                if (expanded) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = nrOfDays.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = imageUrl,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            Icon(
                imageVector = Icons.Filled.Edit,
                modifier = Modifier.align(Alignment.CenterVertically),
                contentDescription = stringResource(R.string.edit_formula_content_description),
                tint = MaterialTheme.colorScheme.secondary,
            )
            Icon(
                imageVector = Icons.Filled.Delete,
                modifier = Modifier.align(Alignment.CenterVertically),
                contentDescription = stringResource(R.string.delete_formula_content_description),
                tint = MaterialTheme.colorScheme.secondary,
            )
            FormulaItemButton(
                expanded = expanded,
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.Top),
            )
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
            tint = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Preview
@Composable
fun FormulaItemPreview() {
    FormulaItem(
        name = "Formula name",
        description = "Formula description",
        nrOfDays = 5,
        price = 100.0,
        imageUrl = "dit_met_een_lange_url_voorstellen",

    )
}
