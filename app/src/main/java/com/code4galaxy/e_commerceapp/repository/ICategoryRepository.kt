package com.code4galaxy.e_commerceapp.repository

import com.code4galaxy.e_commerceapp.model.CategoryResponse

interface ICategoryRepository {
    suspend fun getCategories(): CategoryResponse
}