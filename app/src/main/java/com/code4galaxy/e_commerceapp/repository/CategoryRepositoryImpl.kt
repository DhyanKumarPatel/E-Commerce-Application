package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.CategoryResponse
import com.code4galaxy.e_commerceapp.network.ApiServices

class CategoryRepositoryImpl(private val apiServices: ApiServices) : ICategoryRepository{
    override suspend fun getCategories(): CategoryResponse {
        return apiServices.getCategories()
    }

}