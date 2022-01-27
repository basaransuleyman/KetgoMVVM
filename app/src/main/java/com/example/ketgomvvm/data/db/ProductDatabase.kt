package com.example.ketgomvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ketgomvvm.data.dao.ProductDao
import com.example.ketgomvvm.data.model.ProductModel

@Database(entities = [ProductModel::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}