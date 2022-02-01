package com.example.ketgomvvm.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ketgomvvm.MainActivity
import com.example.ketgomvvm.R
import com.example.ketgomvvm.databinding.FragmentProductDetailBinding
import com.example.ketgomvvm.ui.datastore.ProductManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private lateinit var _binding: FragmentProductDetailBinding
    private val args by navArgs<ProductDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        putBindingData()
        clickUpdateButton()
        closeProductDetailFragment()
        return _binding.root
    }

    private fun closeProductDetailFragment() {
        _binding.ivClose.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailFragment_to_listingFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun putBindingData() {
        with(_binding) {
            tvDetailProduct.text = args.currentProduct.productName
            tvDetailDescription.text = args.currentProduct.productDescription
            tvCreatedDate.text = args.currentProduct.createdDate.toString()
            tvDetailPrice.text = args.currentProduct.productPrice.toString() + " ₺"
            tvDetailLocation.text = args.currentProduct.sellingLocation
            tvDetailCondition.text = args.currentProduct.productStatus
            tvDetailsUp.text = args.currentProduct.upCount.toString()
            Glide.with(requireContext()).load(args.currentProduct.productImage)
                .into(_binding.ivUpdateProduct)
        }
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

}