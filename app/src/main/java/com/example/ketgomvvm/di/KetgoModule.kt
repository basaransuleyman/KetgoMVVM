package com.example.ketgomvvm.di

import android.content.Context
import androidx.room.Room
import com.example.ketgomvvm.data.db.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KetgoModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ProductDatabase::class.java, "ProductDB").build()

    @Singleton
    @Provides
    fun injectDao(
        database: ProductDatabase
    ) = database.productDao()

}