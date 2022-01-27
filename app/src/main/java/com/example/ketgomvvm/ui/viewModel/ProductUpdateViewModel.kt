package com.example.ketgomvvm.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ketgomvvm.data.model.ProductModel
import com.example.ketgomvvm.data.repository.ProductRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductUpdateViewModel @Inject constructor(
    var repository: ProductRepositoryInterface
) : ViewModel() {

    val noteImageUrl = MutableLiveData<String>()

    fun updateProduct(
        productId: Int,
        productName: String,
        productPrice: Int,
        productDescription: String? = null,
        productStatus: String? = null,
        sellingLocation: String,
        productImage: String? = null,
    ) {
        val product = ProductModel(
            productName = productName,
            productPrice = productPrice,
            productDescription = productDescription,
            productStatus = productStatus,
            sellingLocation = sellingLocation,
            productImage = productImage,
            id = productId
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(product)
        }
    }

    fun deleteProductById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProductById(id)
        }
    }

}