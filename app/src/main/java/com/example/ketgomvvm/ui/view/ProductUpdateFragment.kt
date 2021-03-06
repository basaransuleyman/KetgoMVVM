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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ketgomvvm.R
import com.example.ketgomvvm.databinding.FragmentEditProductBinding
import com.example.ketgomvvm.ui.viewModel.ProductUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductUpdateFragment : Fragment() {

    private lateinit var _binding: FragmentEditProductBinding
    private val args by navArgs<ProductDetailFragmentArgs>()
    private val _viewModel by viewModels<ProductUpdateViewModel>()
    private val pickImageCode = 0

    private fun makeAsSold() {
        _binding.btnSold.setOnClickListener {
            _viewModel.updateProductById(id = args.currentProduct.id!!, isSold = true)
            findNavController().navigate(R.id.action_editProductFragment_to_listingFragment)
        }
    }

    private fun clickUpdateProduct() {
        _binding.btnSave.setOnClickListener {
            observeViewModel()
            Toast.makeText(
                context,
                getString(R.string.product_update),
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_editProductFragment_to_listingFragment)
        }
    }

    private fun clickDeleteProductButton() {
        _binding.ivDelete.setOnClickListener {
            _viewModel.deleteProductById(args.currentProduct.id!!)
            Toast.makeText(
                context,
                getString(R.string.product_delete),
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_editProductFragment_to_listingFragment)
        }
    }

    private fun closeUpdateProductFragment() {
        _binding.ivClose.setOnClickListener {
            findNavController().navigate(R.id.action_editProductFragment_to_productDetailFragment)
        }
    }

    private fun putBindingData() {
        with(_binding) {
            etUpdateTitle.setText(args.currentProduct.productName)
            etUpdatePrice.setText(args.currentProduct.productPrice.toString())
            etUpdateDescription.setText(args.currentProduct.productDescription)
            etUpdateLocation.setText(args.currentProduct.sellingLocation)
            Glide.with(requireContext()).load(args.currentProduct.productImage)
                .into(_binding.ivUpdateProduct)
        }
    }

    private fun observeViewModel() {
        _viewModel.updateProduct(
            productId = args.currentProduct.id!!,
            productName = _binding.etUpdateTitle.text.toString(),
            productPrice = _binding.etUpdatePrice.text.toString().toInt(),
            productDescription = _binding.etUpdateDescription.text.toString(),
            sellingLocation = _binding.etUpdateLocation.text.toString(),
            productImage = _viewModel.noteImageUrl.value
        )
    }

    private fun updateNoteImage(uri: Uri?) {
        Glide.with(this).load(uri).into(_binding.ivUpdateProduct)
        _viewModel.noteImageUrl.value = uri.toString()
    }

    private fun updateImage() {
        _binding.ivUpdateProduct.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImageCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageCode) {
            data?.let {
                updateNoteImage(it.data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)

        putBindingData()
        closeUpdateProductFragment()
        clickDeleteProductButton()
        clickUpdateProduct()
        updateImage()
        makeAsSold()
        return _binding.root
    }

}