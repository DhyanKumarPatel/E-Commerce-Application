package com.code4galaxy.e_commerceapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code4galaxy.e_commerceapp.databinding.ItemReviewBinding
import com.code4galaxy.e_commerceapp.model.ProductReview

class ReviewAdapter(private val reviewList: List<ProductReview>): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReviewViewHolder,
        position: Int
    ) {
        holder.bind(reviewList[position])
    }

    override fun getItemCount(): Int {
        return reviewList.size

    }

    class ReviewViewHolder(private val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(review: ProductReview) {
            binding.tvUserName.text =
                review.full_name

            binding.tvReviewTitle.text =
                review.review_title

            binding.tvReview.text =
                review.review

            binding.tvReviewDate.text =
                review.review_date

            binding.ratingBar.rating =
                review.rating.toFloatOrNull() ?: 0f
        }

    }
}