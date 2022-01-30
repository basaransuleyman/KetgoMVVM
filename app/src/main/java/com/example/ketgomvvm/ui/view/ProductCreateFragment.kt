package com.example.ketgomvvm.ui.view

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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ketgomvvm.R
import com.example.ketgomvvm.databinding.FragmentCreateProductBinding
import com.example.ketgomvvm.ui.viewModel.ProductCreateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductCreateFragment : Fragment() {

    private lateinit var _binding: FragmentCreateProductBinding
    private val _viewModel by viewModels<ProductCreateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProductBinding.inflate(inflater, container, false)
        closeProductCreateFragment()
        addImage()
        saveProduct()
        openGoogleMaps()
        return _binding.root
    }

    private fun addProductToDatabase() {
        if (_binding.etCreateTitle.text.toString() == "") {
            Toast.makeText(requireContext(), "error to add db", Toast.LENGTH_SHORT).show()
        } else {
            _viewModel.addProduct(
                productName = _binding.etCreateTitle.text.toString(),
                productPrice = _binding.etCreatePrice.text.toString().toInt(),
                productDescription = _binding.etCreateDescription.text.toString(),
                productImage = _viewModel.noteImageUrl.value,
                productStatus = checkBoxControl(),
                sellingLocation = "asd"
            )
            Toast.makeText(requireContext(), "Success to add db", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_createProductFragment_to_listingFragment)
        }
    }

    private fun openGoogleMaps(){
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
        if (requestCode == PICK_IMAGE) {
            data?.let {
                addNoteImage(it.data)
            }
        }
    }

    private fun addImage() {
        _binding.ivCreateProduct.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE)
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
            _binding.cbCreateFair.isChecked -> return "Fair"
            _binding.cbCreateGood.isChecked -> return "Good"
            _binding.cbCreateLikeNew.isChecked -> return "Like New"
            _binding.cbCreateNew.isChecked -> return "New"
            _binding.cbCreatePoor.isChecked -> return "Poor"
        }
        return "Wrong"
    }

    companion object {
        const val PICK_IMAGE = 1
    }

}