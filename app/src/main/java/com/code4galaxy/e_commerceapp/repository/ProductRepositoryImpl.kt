package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.ProductResponse
import com.code4galaxy.e_commerceapp.network.ApiServices

class ProductRepositoryImpl(private val apiServices: ApiServices): IProductRepository {

    override suspend fun getProducts(subCategoryId: String): ProductResponse {
        return apiServices.getProducts(subCategoryId)
    }

}