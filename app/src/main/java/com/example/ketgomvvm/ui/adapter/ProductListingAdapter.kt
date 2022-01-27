package com.example.ketgomvvm.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ketgomvvm.R
import com.example.ketgomvvm.data.model.ProductModel
import com.example.ketgomvvm.databinding.ListingItemsBinding
import com.example.ketgomvvm.ui.view.ListingFragmentDirections
import java.util.*

class ProductListingAdapter(private var productList: List<ProductModel>) :
    RecyclerView.Adapter<ProductListingAdapter.ProductListHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListHolder {
        val binding = DataBindingUtil.inflate<ListingItemsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.listing_items,
            parent,
            false
        )
        return ProductListHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {

        val currentItem = productList[position]
        var upCount = 0

        holder.binding.product = currentItem

        Glide.with(holder.itemView.context).load(currentItem.productImage.toString())
            .into(holder.binding.ivProduct)

        val action =
            ListingFragmentDirections.actionListingFragmentToProductDetailFragment(currentItem)
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(action)
        }

        holder.binding.ivUp.setOnClickListener {
            upCount++
            holder.binding.tvUp.isVisible = true
            holder.binding.tvUp.text = "$upCount"
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductListHolder(val binding: ListingItemsBinding) :
        RecyclerView.ViewHolder(binding.root)


    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(product: List<ProductModel>) {
        this.productList = product
        notifyDataSetChanged()
    }

}