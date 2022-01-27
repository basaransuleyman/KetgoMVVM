package com.example.ketgomvvm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ketgomvvm.data.model.ProductModel

@Dao
interface ProductDao {
    @Query("SELECT * FROM products_table ORDER BY id DESC")
    fun getAllProducts(): LiveData<List<ProductModel>>

    @Query("DELETE FROM products_table WHERE id = :id")
    suspend fun deleteProductById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductModel)

    @Update
    suspend fun updateProduct(product: ProductModel)
}

