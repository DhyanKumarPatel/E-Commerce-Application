package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.ProductResponse

interface ISearchRepository {
    suspend fun searchProducts(
        searchText: String
    ): ProductResponse
}