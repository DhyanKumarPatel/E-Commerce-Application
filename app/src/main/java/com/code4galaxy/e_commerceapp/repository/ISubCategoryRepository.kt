package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.SubCategoryResponse

interface ISubCategoryRepository {
    suspend fun getSubCategories(categoryId: String): SubCategoryResponse
}