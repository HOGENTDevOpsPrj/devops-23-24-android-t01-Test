package com.example.blanche.data

import android.util.Log
import com.example.blanche.data.database.FormulaDao
import com.example.blanche.data.database.asDbFormula
import com.example.blanche.data.database.asDomainFormula
import com.example.blanche.data.database.asDomainFormulas
import com.example.blanche.model.Formula
import com.example.blanche.model.asApiObject
import com.example.blanche.network.formulas.FormulaApiService
import com.example.blanche.network.formulas.asDbFormula
import com.example.blanche.network.formulas.asDomainObjects
import com.example.blanche.network.formulas.getFormulasAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FormulaRepository {
    // all items from datasource
    suspend fun getFormulas(): Flow<List<Formula>>

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
    override suspend fun getFormulas(): Flow<List<Formula>> {
        // checks the array of items coming in
        // when empty --> tries to fetch from API
        try {
            val formulasApi = formulaApiService.getFormulas()
            Log.i("TEST", "Get formulas from API: ${formulasApi}")
            formulaDao.deleteAll()
            formulasApi.onEach {
                f -> formulaDao.insert(f.asDbFormula())
            }

        } catch (e: Exception) {
            Log.e("TEST", "Error adding formula to API: $e")
        }

        return formulaDao.getAllItems().map {
            it.asDomainFormulas()
        }
    }


    override fun getFormula(id: String): Flow<Formula?> {
        return formulaDao.getItem(id).map {
            it.asDomainFormula()
        }
    }

    override suspend fun addFormula(formula: Formula) {

        Log.i("TEST", "Add formula to API: $formula")
        try {
            formula.copy(id = formulaApiService.addFormula(formula.asApiObject()).message())

            Log.i("TEST", "Added formula to API: $formula")
        } catch (e: Exception) {
            Log.e("TEST", "Error adding formula to API: $e")
            // Handle the error as needed
        }
        formulaDao.insert(formula.asDbFormula())

    }


    override suspend fun deleteFormula(formula: Formula) {
        // Delete from the local database
        // Make the API call to delete the formula from the remote database
        try {
            formulaApiService.deleteFormula(formula.id)
            formulaDao.delete(formula.asDbFormula())
            Log.i("TEST", "Deleted formula from API: $formula")
        } catch (e: Exception) {
            Log.e("TEST", "Error deleting formula from API: $e")
        }
    }

    override suspend fun updateFormula(formula: Formula) {
        formulaDao.update(formula.asDbFormula())
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
                    formulaDao.insert(formula.asDbFormula())
                }
            }
            Log.i("TEST", "Refresh: Data refreshed successfully.")
        } catch (e: Exception) {
            Log.e("TEST", "Refresh: Error - $e")
        }

    }
}