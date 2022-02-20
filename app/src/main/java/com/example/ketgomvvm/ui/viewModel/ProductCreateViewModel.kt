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
class ProductCreateViewModel @Inject constructor(private val repository: ProductRepositoryInterface) :
    ViewModel() {

    private val exampleDateFormat = "dd/M/yyyy"
    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat(exampleDateFormat)
    val noteImageUrl = MutableLiveData<String>()

    fun addProduct(
        productName: String,
        productPrice: Int,
        productDescription: String? = null,
        productStatus: String,
        sellingLocation: String,
        productImage: String? = null
    ) {
        val product = ProductModel(
            productName = productName,
            productPrice = productPrice,
            productDescription = productDescription,
            productStatus = productStatus,
            sellingLocation = sellingLocation,
            productImage = productImage,
            createdDate = dateFormat.format(Date())
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduct(product)
        }
    }

}
