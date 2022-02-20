package com.example.ketgomvvm.ui.viewModel

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

    private val exampleDateFormat = "dd/M/yyyy"
    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat(exampleDateFormat)
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

    fun updateProductById(id: Int, isSold: Boolean? = false, upCount: Int? = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProductById(id, isSold, upCount)
        }
    }

    fun deleteProductById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProductById(id)
        }
    }

}