package com.example.ketgomvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.ketgomvvm.data.dao.ProductDao
import com.example.ketgomvvm.data.model.ProductModel
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: ProductDao
) : ProductRepositoryInterface {

    override fun getAllProducts(): LiveData<List<ProductModel>> {
        return productDao.getAllProducts()
    }

    override suspend fun addProduct(product: ProductModel) {
        return productDao.addProduct(product)
    }

    override suspend fun updateProduct(product: ProductModel) {
        return productDao.updateProduct(product)
    }

    override suspend fun deleteProductById(id: Int) {
        return productDao.deleteProductById(id)
    }

    override suspend fun updateProductById(id: Int, isSold: Boolean?, upCount: Int?) {
        return productDao.updateProductById(id, isSold, upCount)
    }

}


