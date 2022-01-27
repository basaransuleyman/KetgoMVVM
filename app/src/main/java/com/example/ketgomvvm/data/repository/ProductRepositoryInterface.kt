package com.example.ketgomvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.ketgomvvm.data.model.ProductModel
import dagger.hilt.android.AndroidEntryPoint

interface ProductRepositoryInterface {

    fun getAllProducts(): LiveData<List<ProductModel>>

    suspend fun addProduct(product: ProductModel)

    suspend fun updateProduct(product: ProductModel)

    suspend fun deleteProductById(id: Int)

}