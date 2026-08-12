package com.code4galaxy.e_commerceapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.e_commerceapp.databinding.ItemCateoryBinding
import com.code4galaxy.e_commerceapp.model.Category

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

            binding.tvCategoryName.text = category.category_name

            val imageUrl =
                "http://gminnovex.com/myshop/images/${category.category_image_url}"

            Glide.with(binding.ivCategory.context)
                .load(imageUrl)
                .into(binding.ivCategory)

            binding.root.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
}