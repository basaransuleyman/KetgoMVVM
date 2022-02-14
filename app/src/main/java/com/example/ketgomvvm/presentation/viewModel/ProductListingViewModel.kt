package com.example.ketgomvvm.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.ketgomvvm.data.model.ProductModel
import com.example.ketgomvvm.data.repository.ProductRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductListingViewModel @Inject constructor(var repository: ProductRepositoryInterface) :
    ViewModel() {

    val getAllProducts: Flow<List<ProductModel>> = repository.getAllProducts()

}