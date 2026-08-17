package com.code4galaxy.e_commerceapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.ItemCheckoutCartBinding
import com.code4galaxy.e_commerceapp.model.OrderDetailsItem

class OrderDetailsAdapter(
    private val items: List<OrderDetailsItem>
) : RecyclerView.Adapter<OrderDetailsAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(
        private val binding: ItemCheckoutCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderDetailsItem) {

            binding.tvProductName.text =
                item.product_name

            binding.tvUnitPrice.text =
                "Unit Price: $${item.unit_price}"

            binding.tvQuantity.text =
                "Quantity: ${item.quantity}"

            binding.tvAmount.text =
                "Amount: $${item.amount}"

            val imageUrl =
                "http://gminnovex.com/myshop/images/${item.product_image_url}"

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
    ): OrderViewHolder {

        val binding =
            ItemCheckoutCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrderViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}