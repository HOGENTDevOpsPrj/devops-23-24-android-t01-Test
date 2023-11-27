package com.example.blanche.ui.formulas
// FormulaScreen.kt

import androidx.annotation.StringRes
import com.example.blanche.R

enum class FormulaOverviewScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Detail(title = R.string.detail),
    About(title = R.string.about_title),
}
