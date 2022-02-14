package com.example.ketgomvvm.presentation.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ketgomvvm.data.model.ProductModel
import com.example.ketgomvvm.data.repository.ProductRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductUpdateViewModel @Inject constructor(
    var repository: ProductRepositoryInterface
) : ViewModel() {

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat(ProductCreateViewModel.DATE_FORMAT)
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
            createdDate = dateFormat.format(Date()),
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

    fun updateProductById(id: Int, isSold: Boolean? = false, upCount: Int? = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProductById(id, isSold, upCount)
        }
    }

}