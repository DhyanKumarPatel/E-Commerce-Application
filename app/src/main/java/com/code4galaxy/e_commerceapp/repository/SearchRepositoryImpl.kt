package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.ProductResponse
import com.code4galaxy.e_commerceapp.network.ApiServices

class SearchRepositoryImpl(val apiServices: ApiServices): ISearchRepository {
    override suspend fun searchProducts(searchText: String): ProductResponse {
        return apiServices.searchProducts(searchText)
    }

}