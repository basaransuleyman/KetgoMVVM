package com.example.ketgomvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.ketgomvvm.data.model.ProductModel

interface ProductRepositoryInterface {

    fun getAllProducts(): LiveData<List<ProductModel>>

    suspend fun addProduct(product: ProductModel)

    suspend fun updateProduct(product: ProductModel)

    suspend fun deleteProductById(id: Int)

    suspend fun updateProductById(id: Int, isSold: Boolean? = false,upCount:Int?)
}