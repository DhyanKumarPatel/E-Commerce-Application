package com.code4galaxy.e_commerceapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.databinding.ItemCartBinding
import com.code4galaxy.e_commerceapp.model.CartItem


class CartAdapter(private val cartItems: List<CartEntity>,
    private val onPlusClick: (CartEntity) -> Unit,
    private val onMinusClick:(CartEntity) -> Unit): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

   inner class CartViewHolder(private val binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cartItem: CartEntity){
            with(binding){
                tvProductName.text = cartItem.productName
                tvDescription.text = cartItem.description
                tvUnitPrice.text = "$ ${cartItem.unitPrice}"
                tvQuantity.text = cartItem.quantity.toString()

                val unitPrice = cartItem.unitPrice.toDoubleOrNull() ?: 0.0

                val itemTotal = unitPrice * cartItem.quantity

                tvItemTotal.text = "$ $itemTotal"

                val imageUrl = "http://gminnovex.com/myshop/images/\${cartItem.imageUrl}"

                Glide.with(ivProduct.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_cart)
                    .error(R.drawable.image)
                    .into(binding.ivProduct)


                tvPlus.setOnClickListener {
                    onPlusClick(cartItem)
                }

                tvMinus.setOnClickListener {
                    onMinusClick(cartItem)
                }


            }
        }
    }
}