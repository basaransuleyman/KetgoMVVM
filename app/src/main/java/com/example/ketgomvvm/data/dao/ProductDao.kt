package com.example.ketgomvvm.data.dao

import androidx.room.*
import com.example.ketgomvvm.data.model.ProductModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products_table ORDER BY id DESC")
    fun getAllProducts(): Flow<List<ProductModel>>

    @Query("DELETE FROM products_table WHERE id = :id")
    suspend fun deleteProductById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductModel)

    @Update
    suspend fun updateProduct(product: ProductModel)

    @Query("UPDATE products_table SET isSold= :isSold,upCount= :upCount WHERE id= :id ")
    suspend fun updateProductById(id: Int, isSold: Boolean?, upCount: Int?)
}

