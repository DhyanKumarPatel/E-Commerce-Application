package com.code4galaxy.e_commerceapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code4galaxy.e_commerceapp.databinding.ItemAddressBinding
import com.code4galaxy.e_commerceapp.model.Address

class AddressAdapter(
    private val addresses: List<Address>,
    private val onAddressSelected: (Address) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private var selectedPosition = -1

    inner class AddressViewHolder(
        private val binding: ItemAddressBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address, position: Int) {

            binding.tvAddressTitle.text = address.title
            binding.tvAddress.text = address.address

            binding.rbAddress.isChecked =
                position == selectedPosition

            binding.rbAddress.setOnClickListener {

                val oldPosition = selectedPosition

                selectedPosition = bindingAdapterPosition

                if (oldPosition != -1) {
                    notifyItemChanged(oldPosition)
                }
                notifyItemChanged(selectedPosition)

                onAddressSelected(address)
            }

            binding.root.setOnClickListener {
                binding.rbAddress.performClick()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressViewHolder {

        val binding =
            ItemAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AddressViewHolder,
        position: Int
    ) {
        holder.bind(addresses[position], position)
    }

    override fun getItemCount(): Int {
        return addresses.size
    }
}