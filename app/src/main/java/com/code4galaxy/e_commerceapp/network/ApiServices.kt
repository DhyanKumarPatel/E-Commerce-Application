package com.code4galaxy.e_commerceapp.network

import com.code4galaxy.e_commerceapp.model.CategoryResponse
import com.code4galaxy.e_commerceapp.model.ProductResponse
import com.code4galaxy.e_commerceapp.model.SubCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {


    @GET("Category")
    suspend fun getCategories(): CategoryResponse

    @GET("SubCategory")
    suspend fun getSubCategories(
        @Query("category_id") categoryId: String
    ): SubCategoryResponse


    @GET("SubCategory/products/{sub_category_id}")
    suspend fun getProducts(
        @Path("sub_category_id") subCategoryId: String
    ): ProductResponse
}