package com.code4galaxy.e_commerceapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.ItemProductImageBinding
import com.code4galaxy.e_commerceapp.model.ProductImage

class ProductImageAdapter(private val imageList: List<ProductImage>): RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductImageViewHolder {
        val binding = ItemProductImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)


        return ProductImageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductImageViewHolder,
        position: Int
    ) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
       return imageList.size
    }

    class ProductImageViewHolder(
        private val binding: ItemProductImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(productImage: ProductImage) {

            val imageUrl =
                "http://gminnovex.com/myshop/images/${productImage.image}"

            Glide.with(binding.ivProductImage.context)
                .load(imageUrl)
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
                .into(binding.ivProductImage)
        }
    }
}