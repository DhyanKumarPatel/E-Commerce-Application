package com.code4galaxy.e_commerceapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.code4galaxy.e_commerceapp.model.Product
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.ItemProductBinding

class ProductAdapter(private val product: List<Product>): RecyclerView.Adapter<ProductAdapter.ProductViewAdapter>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewAdapter {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewAdapter(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewAdapter,
        position: Int
    ) {
        holder.bind(product[position])
    }

    override fun getItemCount(): Int {
        return product.size
    }

    inner class ProductViewAdapter(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product) {
            binding.ivProduct.setImageResource(R.drawable.ic_launcher_foreground)
            binding.tvProductName.text = product.name
            binding.tvDescription.text = product.description
            binding.tvPrice.text = product.price
            binding.ratingBar.rating = product.rating

            updateQuantityUI(product)


            binding.tvAddToCart.setOnClickListener {

                product.quantity = 1

                updateQuantityUI(product)

                Toast.makeText(
                    binding.root.context,
                    "${product.name} added to cart",
                    Toast.LENGTH_SHORT
                ).show()
            }


            binding.tvPlus.setOnClickListener {

                product.quantity++

                updateQuantityUI(product)
            }


            binding.tvMinus.setOnClickListener {

                if (product.quantity > 1) {

                    product.quantity--

                } else {

                    product.quantity = 0
                }

                updateQuantityUI(product)
            }
        }

        private fun updateQuantityUI(product: Product) {

            if (product.quantity == 0) {


                binding.tvAddToCart.visibility = View.VISIBLE


                binding.tvMinus.visibility = View.GONE
                binding.tvQuantity.visibility = View.GONE
                binding.tvPlus.visibility = View.GONE

            } else {


                binding.tvAddToCart.visibility = View.GONE


                binding.tvMinus.visibility = View.VISIBLE
                binding.tvQuantity.visibility = View.VISIBLE
                binding.tvPlus.visibility = View.VISIBLE


                binding.tvQuantity.text =
                    product.quantity.toString()
            }
        }
    }
}