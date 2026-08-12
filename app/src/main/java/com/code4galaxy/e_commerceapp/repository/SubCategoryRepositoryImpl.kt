package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.SubCategoryResponse
import com.code4galaxy.e_commerceapp.network.ApiServices

class SubCategoryRepositoryImpl(private val apiServices: ApiServices): ISubCategoryRepository {
    override suspend fun getSubCategories(categoryId: String): SubCategoryResponse {

        return apiServices.getSubCategories(categoryId)
    }
}