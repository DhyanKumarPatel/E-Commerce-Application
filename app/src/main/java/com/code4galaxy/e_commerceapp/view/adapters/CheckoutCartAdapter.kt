package com.code4galaxy.e_commerceapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.databinding.ItemCheckoutCartBinding

class CheckoutCartAdapter(
    private val cartItems: List<CartEntity>
) : RecyclerView.Adapter<CheckoutCartAdapter.CartViewHolder>() {

    inner class CartViewHolder(
        private val binding: ItemCheckoutCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartEntity) {

            binding.tvProductName.text =
                item.productName

            binding.tvUnitPrice.text =
                "Unit Price: $${item.unitPrice}"

            binding.tvQuantity.text =
                "Quantity: ${item.quantity}"

            val amount =
                item.unitPrice.toDouble() * item.quantity

            binding.tvAmount.text =
                "Amount: $${amount}"

            val imageUrl =
                "http://gminnovex.com/myshop/images/${item.imageUrl}"

            Glide.with(binding.ivProduct.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_cart)
                .error(R.drawable.image)
                .into(binding.ivProduct)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {

        val binding =
            ItemCheckoutCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

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
}