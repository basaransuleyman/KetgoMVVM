package com.example.ketgomvvm.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ketgomvvm.R
import com.example.ketgomvvm.data.model.ProductModel
import com.example.ketgomvvm.databinding.FragmentListingBinding
import com.example.ketgomvvm.ui.adapter.ProductListingAdapter
import com.example.ketgomvvm.ui.viewModel.ProductListingViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class ListingFragment : BaseFragment() {

    private lateinit var _binding: FragmentListingBinding
    private lateinit var _productAdapter: ProductListingAdapter
    private val _productArrayList: ArrayList<ProductModel> = ArrayList()
    private val _viewModel by viewModels<ProductListingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListingBinding.inflate(inflater, container, false)
        _productAdapter = ProductListingAdapter(_productArrayList)
        bottomTabNavigation()
        bindingRecyclerView()
        readNote()
        return _binding.root
    }

    private fun bindingRecyclerView() {
        with(_binding) {
            rcListing.layoutManager = LinearLayoutManager(context)
            rcListing.adapter = _productAdapter
        }
    }

    private fun readNote() {
        _viewModel.getAllProducts.observe(viewLifecycleOwner, { products ->
            _productAdapter.setNotes(products)
        })
    }

    private fun bottomTabNavigation() {
        _binding.cnbTabNav.setOnItemSelectedListener {
            when (it) {
                R.id.sold -> Navigation.findNavController(_binding.root)
                    .navigate(ListingFragmentDirections.actionListingFragmentToHomeFragment())
                R.id.add -> Navigation.findNavController(_binding.root)
                    .navigate(ListingFragmentDirections.actionListingFragmentToCreateProductFragment())
                R.id.list -> Navigation.findNavController(_binding.root)
                    .navigate(ListingFragmentDirections.actionListingFragmentSelf())
            }
        }
    }
}