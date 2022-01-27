package com.example.ketgomvvm.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ketgomvvm.data.model.ProductModel
import com.example.ketgomvvm.data.repository.ProductRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(var repository: ProductRepositoryInterface) :
    ViewModel() {

    val getAllProducts: LiveData<List<ProductModel>>

    init {
        getAllProducts = repository.getAllProducts()
    }
}