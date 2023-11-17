package com.example.blanche.data

import com.example.blanche.model.Formula
import com.example.blanche.network.FormulaApiService
import com.example.blanche.network.asDomainObjects

interface FormulaRepository {
    suspend fun getFormulas(): List<Formula>
}

class ApiFormulasRepository(
    private val formulaApiService: FormulaApiService,
) : FormulaRepository {
    override suspend fun getFormulas(): List<Formula> {
        return formulaApiService.getFormulas().asDomainObjects()
    }
}
