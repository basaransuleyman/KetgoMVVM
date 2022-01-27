package com.example.ketgomvvm.di

import com.example.ketgomvvm.data.repository.ProductRepository
import com.example.ketgomvvm.data.repository.ProductRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class  InterfaceModule {
    @Binds
    abstract fun injectInterface(productInterface : ProductRepository) : ProductRepositoryInterface
}