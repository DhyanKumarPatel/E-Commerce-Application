package com.code4galaxy.e_commerceapp.network

import com.code4galaxy.e_commerceapp.model.AddAddressRequest
import com.code4galaxy.e_commerceapp.model.AddAddressResponse
import com.code4galaxy.e_commerceapp.model.AddressResponse
import com.code4galaxy.e_commerceapp.model.CategoryResponse
import com.code4galaxy.e_commerceapp.model.LoginRequest
import com.code4galaxy.e_commerceapp.model.LoginResponse
import com.code4galaxy.e_commerceapp.model.OrderDetailsResponse
import com.code4galaxy.e_commerceapp.model.OrderRequest
import com.code4galaxy.e_commerceapp.model.OrderResponse
import com.code4galaxy.e_commerceapp.model.ProductDetailsResponse
import com.code4galaxy.e_commerceapp.model.ProductResponse
import com.code4galaxy.e_commerceapp.model.RegisterRequest
import com.code4galaxy.e_commerceapp.model.RegisterResponse
import com.code4galaxy.e_commerceapp.model.SubCategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("Product/details/{product_id}")
    suspend fun getProductDetails(
        @Path("product_id") productId: String
    ): ProductDetailsResponse


    @POST("User/register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("User/auth")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("Product/search")
    suspend fun searchProducts(
        @Query("query") searchText: String
    ): ProductResponse

    @POST("Order")
    suspend fun placeOrder(
        @Body request: OrderRequest
    ): OrderResponse

    @GET("User/addresses/{userId}")
    suspend fun getAddresses(
        @Path("userId") userId: String
    ): AddressResponse


    @POST("User/address")
    suspend fun addAddress(
        @Body request: AddAddressRequest
    ): AddAddressResponse



    @GET("Order")
    suspend fun getOrderDetails(
        @Query("order_id") orderId: Int
    ): OrderDetailsResponse

}