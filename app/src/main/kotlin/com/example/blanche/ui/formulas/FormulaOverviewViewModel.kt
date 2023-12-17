package com.example.blanche.ui.formulas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.blanche.BlancheAdminApplication
import com.example.blanche.data.FormulaRepository
import com.example.blanche.model.Formula
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class FormulaOverviewViewModel(private val formulaRepository: FormulaRepository) : ViewModel() {
    // use StateFlow (Flow: emits current state + any updates)
    /*
    * Note: uiState is a cold flow. Changes don't come in from above unless a
    * refresh is called...
    * */
    private val _uiState = MutableStateFlow(FormulaOverviewState())
    val uiState: StateFlow<FormulaOverviewState> = _uiState.asStateFlow()

    private val _showEditFormulaScreen = MutableStateFlow(false)
    val showEditFormulaScreen: StateFlow<Boolean> = _showEditFormulaScreen

    fun toggleEditFormulaScreen() {
        _showEditFormulaScreen.value = !_showEditFormulaScreen.value
    }

    fun setFormula(formula: Formula) {
        setFormulaId(formula.id)
        setNewFormulaName(formula.name)
        setNewFormulaDescription(formula.description)
        setNewFormulaPrice(formula.price)
        setNewPricePerDays(formula.pricePerDays)
        setNewPricePerExtraDay(formula.pricePerExtraDay)
    }

    /*
  * Note: uiListState is a hot flow (.stateIn makes it so) --> it updates given a scope (viewmodelscope)
  * when no updates are required (lifecycle) the subscription is stopped after a timeout
  * */
    lateinit var uiListState: StateFlow<FormulaListState>

    // keeping the state of the api request
    var formulaApiState: FormulaApiState by mutableStateOf(FormulaApiState.Loading)
        private set

    init {
        // initializes the uiListState
        getRepoFormulas()
    }

    fun editFormula(formula: Formula){
        viewModelScope.launch {
            formulaRepository.updateFormula(formula)
        }
    }

    fun deleteFormula(formula: Formula){
        viewModelScope.launch {
            formulaRepository.deleteFormula(formula)
        }
    }
/*
    var showDeleteConfirmDialog by mutableStateOf(false)
        private set

    fun confirmDeleteFormula(formula: Formula){
        showDeleteConfirmDialog = true
    }

    fun cancelDeleteFormula(){
        showDeleteConfirmDialog = false
    }

    */

    fun addFormula() {
        // saving the new task (to db? to network? --> doesn't matter
        viewModelScope.launch {
            saveFormula(
                Formula(
                    _uiState.value.newFormulaId,
                    _uiState.value.newFormulaName,
                    _uiState.value.newFormulaDescription,
                    _uiState.value.newFormulaPrice,
                    _uiState.value.newFormulaImageUrl,
                    _uiState.value.newFormulaHasDrinks,
                    _uiState.value.newFormulaHasFood,
                    _uiState.value.newFormulaPricePerDays,
                    _uiState.value.newFormulaPricePerExtraDay,
                ),
            )
        }
        _uiState.update { currentState ->
            currentState.copy(
                newFormulaName = "",
                newFormulaDescription = "",
                newFormulaPrice = 0.0,
                newFormulaImageUrl = "",
                newFormulaHasDrinks = false,
                newFormulaHasFood = false,
                newFormulaPricePerDays = mapOf(0 to 0.0),
                newFormulaPricePerExtraDay = 0.0,
                scrollActionIdx = currentState.scrollActionIdx.plus(1),
                scrollToItemIndex = uiListState.value.formulaList.size,
            )
        }
    }

    fun updateFormula() {
        viewModelScope.launch {
            updateFormula(
                Formula(
                    _uiState.value.newFormulaId,
                    _uiState.value.newFormulaName,
                    _uiState.value.newFormulaDescription,
                    _uiState.value.newFormulaPrice,
                    _uiState.value.newFormulaImageUrl,
                    _uiState.value.newFormulaHasDrinks,
                    _uiState.value.newFormulaHasFood,
                    _uiState.value.newFormulaPricePerDays,
                    _uiState.value.newFormulaPricePerExtraDay,
                ),
            )
        }
    }

    private fun validateInput(): Boolean {
        return with(_uiState) {
            value.newFormulaName.isNotBlank() && value.newFormulaDescription.isNotBlank()
        }
    }

    fun setFormulaId(id: String) {
        _uiState.update { it.copy(newFormulaId = id) }
    }

    fun setNewFormulaName(newName: String) {
        _uiState.update { it.copy(newFormulaName = newName) }
    }

    fun setNewFormulaDescription(newFormulaDescription: String) {
        _uiState.update {
            it.copy(newFormulaDescription = newFormulaDescription)
        }
    }

    fun setNewFormulaNrOfDays(newFormulaNrOfDays: Int) {
        _uiState.update {
            it.copy(newFormulaNrOfDays = newFormulaNrOfDays)
        }
    }

    fun setNewFormulaPrice(newFormulaPrice: Double) {
        _uiState.update {
            it.copy(newFormulaPrice = newFormulaPrice)
        }
    }

    fun setNewPricePerDays(newFormulaPricePerDays: Map<Int, Double>) {
        _uiState.update {
            it.copy(newFormulaPricePerDays = newFormulaPricePerDays)
        }
    }

    fun setNewPricePerExtraDay(newPricePerExtraDay: Double) {
        _uiState.update {
            it.copy(newFormulaPricePerExtraDay = newPricePerExtraDay)
        }
    }

    fun setNewFormulaImageUrl(newFormulaImageUrl: String) {
        _uiState.update {
            it.copy(newFormulaImageUrl = newFormulaImageUrl)
        }
    }

    // this
    private fun getRepoFormulas() {
        try {
            viewModelScope.launch { formulaRepository.refresh() }

            uiListState = formulaRepository.getFormulas().map { FormulaListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = FormulaListState(),
                )
            formulaApiState = FormulaApiState.Success
        } catch (e: IOException) {
            // show a toast? save a log on firebase? ...
            // set the error state
            formulaApiState = FormulaApiState.Error
        }
    }

    private suspend fun saveFormula(formula: Formula) {
        if (validateInput()) {
            formulaRepository.addFormula(formula)
        }
    }

    private suspend fun updateFormula(formula: Formula) {
        if (validateInput()) {
            formulaRepository.updateFormula(formula)
        }
    }

    // object to tell the android framework how to handle the parameter of the viewmodel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BlancheAdminApplication)
                val formulaRepository = application.container.formulaRepository
                FormulaOverviewViewModel(
                    formulaRepository = formulaRepository,
                )
            }
        }
    }
}
