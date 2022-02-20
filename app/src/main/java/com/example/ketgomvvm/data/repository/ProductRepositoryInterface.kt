package com.example.ketgomvvm.data.repository

import com.example.ketgomvvm.data.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductRepositoryInterface {

    fun getAllProducts(): Flow<List<ProductModel>>

    suspend fun addProduct(product: ProductModel)

    suspend fun updateProduct(product: ProductModel)

    suspend fun deleteProductById(id: Int)

    suspend fun updateProductById(id: Int, isSold: Boolean? = false,upCount:Int?)
}