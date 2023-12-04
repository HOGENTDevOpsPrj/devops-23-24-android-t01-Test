package com.example.blanche.ui.formulas

import com.example.blanche.model.Formula

// the data class just holds the (immutable) values of the state
data class FormulaOverviewState(
    val newFormulaId: String = "",
    val newFormulaName: String = "",
    val newFormulaDescription: String = "",
    val newFormulaNrOfDays: Int = 0,
    val newFormulaPrice: Double = 0.0,
    val newFormulaImageUrl: String = "",
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
)

data class FormulaListState(val formulaList: List<Formula> = listOf())

// the sealed interface has only three possible values
/*Sidenote: to learn more about this TaskApiState object, you can search on LCE (Loading, Content, Error) pattern

When the state is changed to Error, the taskList will not be updated (offline first).
To ensure the list is considered immutable (fully immutable, won't ever change unless a new object is created), add the Immutable annotation.

The LCE pattern is not completed in the application, because it requires more complex helper classes
An example can be found here https://www.valueof.io/blog/compose-ui-state-flow-offline-first-repository
*/

sealed interface FormulaApiState {
    object Success : FormulaApiState
    object Error : FormulaApiState
    object Loading : FormulaApiState
}
