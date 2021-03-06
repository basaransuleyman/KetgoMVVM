package com.example.ketgomvvm.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ketgomvvm.R
import com.example.ketgomvvm.databinding.FragmentProductDetailBinding
import com.example.ketgomvvm.ui.viewModel.ProductUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private lateinit var _binding: FragmentProductDetailBinding
    private val args by navArgs<ProductDetailFragmentArgs>()
    private val _viewModel by viewModels<ProductUpdateViewModel>()
    private var upCountStart = 0

    private fun closeProductDetailFragment() {
        _binding.ivClose.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailFragment_to_listingFragment)
        }
    }

    private fun clickedThumbsUp() {
        _binding.ivUp.setOnClickListener {
            upCountStart++
            with(_binding) {
                tvDetailsUp.isVisible = true
                tvDetailsUp.text = "$upCountStart"
            }
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        _viewModel.updateProductById(
            id = args.currentProduct.id!!,
            upCount = _binding.tvDetailsUp.text.toString().toInt()
        )
    }

    private fun clickUpdateButton() {
        _binding.ivUpdateProductButton.setOnClickListener {
            val action =
                ProductDetailFragmentDirections.actionProductDetailFragmentToEditProductFragment(
                    args.currentProduct
                )
            findNavController().navigate(action)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun putBindingData() {
        with(_binding) {
            tvDetailProduct.text = args.currentProduct.productName
            tvDetailDescription.text = args.currentProduct.productDescription
            tvCreatedDate.text = args.currentProduct.createdDate.toString()
            tvDetailPrice.text =
                args.currentProduct.productPrice.toString() + getString(R.string.tl)
            tvDetailLocation.text = args.currentProduct.sellingLocation
            tvDetailCondition.text = args.currentProduct.productStatus
            tvDetailsUp.text = args.currentProduct.upCount.toString()
            Glide.with(requireContext()).load(args.currentProduct.productImage)
                .into(_binding.ivUpdateProduct)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        putBindingData()
        clickUpdateButton()
        closeProductDetailFragment()
        clickedThumbsUp()
        return _binding.root
    }

}