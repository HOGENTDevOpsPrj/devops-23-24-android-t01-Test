package com.example.blanche.ui.formulas
// FormulaScreen.kt

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.blanche.R

enum class FormulaOverviewScreen(@StringRes val title: Int, val icon: ImageVector) {
    Start(title = R.string.formulas, icon = Icons.Filled.Check),
    Detail(title = R.string.detail, Icons.Filled.Check),
}
