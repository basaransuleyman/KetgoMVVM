package com.example.ketgomvvm.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.ketgomvvm.R
import com.example.ketgomvvm.databinding.FragmentSoldBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SoldFragment : BaseFragment() {

    private lateinit var _binding: FragmentSoldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoldBinding.inflate(inflater, container, false)
        bottomTabNavigation()
        return _binding.root
    }


    private fun bottomTabNavigation() {
        _binding.cnbTabNav.setOnItemSelectedListener {
            when (it) {
                R.id.sold -> Navigation.findNavController(_binding.root)
                    .navigate(SoldFragmentDirections.actionHomeFragmentSelf())
                R.id.add -> Navigation.findNavController(_binding.root)
                    .navigate(SoldFragmentDirections.actionHomeFragmentToCreateProductFragment())
                R.id.list -> Navigation.findNavController(_binding.root)
                    .navigate(SoldFragmentDirections.actionHomeFragmentToListingFragment())
            }
        }
    }

}