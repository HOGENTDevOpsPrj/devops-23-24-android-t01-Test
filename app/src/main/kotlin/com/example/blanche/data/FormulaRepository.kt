package com.example.blanche.data

import android.util.Log
import com.example.blanche.data.database.FormulaDao
import com.example.blanche.data.database.asDbFormula
import com.example.blanche.data.database.asDomainFormula
import com.example.blanche.data.database.asDomainFormulas
import com.example.blanche.model.Formula
import com.example.blanche.network.formulas.FormulaApiService
import com.example.blanche.network.formulas.asDomainObjects
import com.example.blanche.network.formulas.getFormulasAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface FormulaRepository {
    // all items from datasource
    fun getFormulas(): Flow<List<Formula>>

    // single item from datasource
    fun getFormula(id: String): Flow<Formula?>

    suspend fun addFormula(formula: Formula)
    suspend fun deleteFormula(formula: Formula)
    suspend fun updateFormula(formula: Formula)
    suspend fun refresh()
}

class CachingFormulaRepository(
    private val formulaDao: FormulaDao,
    private val formulaApiService: FormulaApiService,
) : FormulaRepository {

    // this repo contains logic to refresh the formulas (remote)
    // sometimes that logic is written in a 'usecase'
    override fun getFormulas(): Flow<List<Formula>> {
        // checks the array of items coming in
        // when empty --> tries to fetch from API
        return formulaDao.getAllItems().map {
            it.asDomainFormulas()
        }.onEach {
            if (it.isEmpty()) {
                refresh()
            }
        }
    }

    override fun getFormula(id: String): Flow<Formula?> {
        return formulaDao.getItem(id).map {
            it.asDomainFormula()
        }
    }

    override suspend fun addFormula(formula: Formula) {
        // add to the local database
        formulaDao.insert(formula.asDbFormula())

        // Make the API call to add the formula to the remote database
        try {
            //formulaApiService.addFormula(formula)
            Log.i("TEST", "Added formula to API: $formula")
        } catch (e: Exception) {
            Log.e("TEST", "Error adding formula to API: $e")
            // Handle the error as needed
        }
    }


    override suspend fun deleteFormula(formula: Formula) {
        // Delete from the local database
        formulaDao.delete(formula.asDbFormula())

        // Make the API call to delete the formula from the remote database
        try {
            formulaApiService.deleteFormula(formula.id)
            Log.i("TEST", "Deleted formula from API: $formula")
        } catch (e: Exception) {
            Log.e("TEST", "Error deleting formula from API: $e")
        }
    }

    override suspend fun updateFormula(formula: Formula) {
        formulaDao.update(formula.asDbFormula())
        //Log.i("TEST", "Updated formula in API: $formula")
        try {
            formulaApiService.updateFormula(formula.id, formula)
            Log.i("TEST", "Updated formula in API: $formula")
        } catch (e: Exception) {
            Log.i("Id", "$formula")
            Log.e("TEST", "Error updating formula in API: $e")
        }
    }


    override suspend fun refresh() {
        try {
            formulaApiService.getFormulasAsFlow().asDomainObjects().collect { value ->
                for (formula in value) {
                    Log.i("TEST", "Refresh: Adding formula - $formula")
                    addFormula(formula)
                }
            }
            Log.i("TEST", "Refresh: Data refreshed successfully.")
        } catch (e: Exception) {
            Log.e("TEST", "Refresh: Error - $e")
        }

    }
}