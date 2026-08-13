package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.ProductDetailsResponse
import com.code4galaxy.e_commerceapp.network.ApiServices

class ProductDetailsRepositoryImpl(private val apiServices: ApiServices): IProductDetailsRepository {
    override suspend fun getProductDetails(productId: String): ProductDetailsResponse {
         return apiServices.getProductDetails(productId)
    }

}