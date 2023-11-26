package com.example.blanche.data

import android.util.Log
import com.example.blanche.data.database.FormulaDao
import com.example.blanche.data.database.asDbTask
import com.example.blanche.data.database.asDomainTask
import com.example.blanche.data.database.asDomainTasks
import com.example.blanche.model.Formula
import com.example.blanche.network.FormulaApiService
import com.example.blanche.network.asDomainObjects
import com.example.blanche.network.getFormulasAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface FormulaRepository {
    // all items from datasource
    fun getFormulas(): Flow<List<Formula>>

    // single item from datasource
    fun getFormula(id: Int): Flow<Formula?>

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
        // checkes the array of items comming in
        // when empty --> tries to fetch from API
        return formulaDao.getAllItems().map {
            it.asDomainTasks()
        }.onEach {
            if (it.isEmpty()) {
                refresh()
            }
        }
    }

    override fun getFormula(id: Int): Flow<Formula?> {
        return formulaDao.getItem(id).map {
            it.asDomainTask()
        }
    }

    override suspend fun addFormula(formula: Formula) {
        formulaDao.insert(formula.asDbTask())
    }

    override suspend fun deleteFormula(formula: Formula) {
        formulaDao.delete(formula.asDbTask())
    }

    override suspend fun updateFormula(formula: Formula) {
        formulaDao.update(formula.asDbTask())
    }

    override suspend fun refresh() {
        formulaApiService.getFormulasAsFlow().asDomainObjects().collect { value ->
            for (formula in value) {
                Log.i("TEST", "refresh: $value")
                addFormula(formula)
            }
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
