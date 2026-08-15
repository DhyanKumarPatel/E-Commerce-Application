package com.code4galaxy.e_commerceapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.databinding.ItemProductBinding
import com.code4galaxy.e_commerceapp.model.Product

class ProductAdapter(private val productList: List<Product>,
                     private val cartItems: List<CartEntity>,
                     private val onProductClick:(Product) -> Unit,
                     private val onAddToCartClick:(Product) -> Unit,
                     private val onPlusClick: (Product, Int) -> Unit,
                     private val onMinusClick: (Product, Int) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
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
                .error(R.drawable.image)
                .into(binding.ivProduct)

            val cartItem = cartItems.find {
                it.productId == product.product_id
            }

            val quantity =  cartItem?.quantity ?: 0

            if (quantity == 0) {

                binding.tvAddToCart.visibility = View.VISIBLE

                binding.tvMinus.visibility = View.GONE

                binding.tvQuantity.visibility = View.GONE

                binding.tvPlus.visibility = View.GONE

            } else {

                binding.tvAddToCart.visibility = View.GONE

                binding.tvMinus.visibility = View.VISIBLE

                binding.tvQuantity.visibility = View.VISIBLE

                binding.tvPlus.visibility = View.VISIBLE

                binding.tvQuantity.text = quantity.toString()
            }


            binding.root.setOnClickListener {
                onProductClick(product)
            }
            binding.tvAddToCart.setOnClickListener {
                onAddToCartClick(product)
            }

            binding.tvPlus.setOnClickListener {
                onPlusClick(product, quantity)
            }

            binding.tvMinus.setOnClickListener {
                onMinusClick(product,quantity)
            }
        }

    }


}