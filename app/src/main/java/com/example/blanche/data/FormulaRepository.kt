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
import java.net.SocketTimeoutException

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
        formulaDao.insert(formula.asDbFormula())
    }

    override suspend fun deleteFormula(formula: Formula) {
        formulaDao.delete(formula.asDbFormula())
    }

    override suspend fun updateFormula(formula: Formula) {
        formulaDao.update(formula.asDbFormula())
    }

    override suspend fun refresh() {
        try{
            formulaApiService.getFormulasAsFlow().asDomainObjects().collect { value ->
                for (formula in value) {
                    Log.i("TEST", "refresh: $value")
                    addFormula(formula)
                }
            }
        }catch (e: SocketTimeoutException){
            Log.i("TEST", "refresh: $e")
        }

    }
}

/*class ApiFormulasRepository(
    private val formulaApiService: FormulaApiService,
) : FormulaRepository {
    override suspend fun getFormulas(): List<Formula> {
        return formulaApiService.getFormulas().asDomainObjects()
    }
}*/
