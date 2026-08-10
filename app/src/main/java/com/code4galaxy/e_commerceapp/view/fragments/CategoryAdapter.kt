package com.code4galaxy.e_commerceapp.view.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code4galaxy.e_commerceapp.model.Category
import com.code4galaxy.e_commerceapp.databinding.ItemCateoryBinding

class CategoryAdapter(
    private val categoryList: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {

        val binding = ItemCateoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class CategoryViewHolder(
        private val binding: ItemCateoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.ivCategory.setImageResource(category.image)
            binding.tvCategoryName.text = category.name

            binding.root.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
}