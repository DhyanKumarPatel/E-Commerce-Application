package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.ProductDetailsResponse

interface IProductDetailsRepository {
    suspend fun getProductDetails(productId: String): ProductDetailsResponse
}