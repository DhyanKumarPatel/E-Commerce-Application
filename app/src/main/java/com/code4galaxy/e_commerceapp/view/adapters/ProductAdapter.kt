package com.code4galaxy.e_commerceapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.ItemProductBinding
import com.code4galaxy.e_commerceapp.model.Product

class ProductAdapter(private val productList: List<Product>,
    private val onProductClick:(Product) -> Unit,
    private val onAddToCartClick:(Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(private val binding : ItemProductBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product){
            binding.tvProductName.text = product.product_name

            binding.tvDescription.text = product.description

            binding.tvPrice.text = "$ ${product.price}"

            binding.ratingBar.rating = product.average_rating.toFloatOrNull() ?: 0f

            val imageUrl = "http://gminnovex.com/myshop/images/${product.product_image_url}"

            Glide.with(binding.ivProduct.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_cart)
                .into(binding.ivProduct)

            binding.root.setOnClickListener {
                onProductClick(product)
            }
            binding.tvAddToCart.setOnClickListener {
                onAddToCartClick(product)
            }
        }

    }


}