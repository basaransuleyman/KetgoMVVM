package com.example.ketgomvvm.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ketgomvvm.R
import com.example.ketgomvvm.databinding.FragmentCreateProductBinding
import com.example.ketgomvvm.data.datastore.ProductManager
import com.example.ketgomvvm.presentation.viewModel.ProductCreateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductCreateFragment : Fragment() {

    private lateinit var _binding: FragmentCreateProductBinding
    private val _viewModel by viewModels<ProductCreateViewModel>()
    private lateinit var _productManager: ProductManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProductBinding.inflate(inflater, container, false)
        _productManager = ProductManager(requireContext())

        closeProductCreateFragment()
        addImage()
        saveProduct()
        openGoogleMaps()
        observeFromDatastore()
        return _binding.root
    }

    private fun addProductToDatabase() {
        if (_binding.etCreateTitle.text.toString() == getString(R.string.empty_string)) {
            Toast.makeText(
                requireContext(),
                getString(R.string.error_add_data_to_db),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            _viewModel.addProduct(
                productName = _binding.etCreateTitle.text.toString(),
                productPrice = _binding.etCreatePrice.text.toString().toInt(),
                productDescription = _binding.etCreateDescription.text.toString(),
                productImage = _viewModel.noteImageUrl.value,
                productStatus = checkBoxControl(),
                sellingLocation = _binding.etCreateLocation.text.toString()
            )
            Toast.makeText(
                requireContext(),
                getString(R.string.product_created),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_createProductFragment_to_listingFragment)
        }
    }

    private fun observeFromDatastore() {
        _productManager.productSellingLocationFlow.asLiveData()
            .observe(viewLifecycleOwner) { location ->
                location?.let {
                    _binding.etCreateLocation.setText(location)
                }
            }
    }

    private fun openGoogleMaps() {
        _binding.etCreateLocation.setOnClickListener {
            findNavController().navigate(R.id.action_createProductFragment_to_mapsActivity)
        }
    }

    private fun addNoteImage(uri: Uri?) {
        Glide.with(this).load(uri).into(_binding.ivCreateProduct)
        _viewModel.noteImageUrl.value = uri.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_CODE) {
            data?.let {
                addNoteImage(it.data)
            }
        }
    }

    private fun addImage() {
        _binding.ivCreateProduct.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,PICK_IMAGE_CODE)
        }
    }

    private fun saveProduct() {
        _binding.btnSave.setOnClickListener {
            addProductToDatabase()
        }
    }

    private fun closeProductCreateFragment() {
        _binding.ivClose.setOnClickListener {
            findNavController().navigate(R.id.action_createProductFragment_to_listingFragment)
        }
    }

    private fun checkBoxControl(): String {
        when {
            _binding.cbCreateFair.isChecked -> return getString(R.string.condition_fair)
            _binding.cbCreateGood.isChecked -> return getString(R.string.condition_good)
            _binding.cbCreateLikeNew.isChecked -> return getString(R.string.like_new)
            _binding.cbCreateNew.isChecked -> return getString(R.string.condition_new)
            _binding.cbCreatePoor.isChecked -> return getString(R.string.condition_poor)
        }
        return getString(R.string.warning)
    }

    companion object {
        const val PICK_IMAGE_CODE = 1
    }

}