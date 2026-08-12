package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.ProductResponse

interface IProductRepository {
    suspend fun getProducts(
        subCategoryId: String
    ): ProductResponse
}