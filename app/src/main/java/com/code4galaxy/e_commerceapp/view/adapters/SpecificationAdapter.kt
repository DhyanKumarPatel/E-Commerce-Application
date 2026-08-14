import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code4galaxy.e_commerceapp.databinding.ItemSpecificationBinding
import com.code4galaxy.e_commerceapp.model.ProductSpecification

class SpecificationAdapter(private val specificationList: List<ProductSpecification>):
RecyclerView.Adapter<SpecificationAdapter.SpecificationViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecificationViewHolder {
        val binding = ItemSpecificationBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return SpecificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SpecificationViewHolder,
        position: Int
    ) {
        holder.bind(specificationList[position])
    }

    override fun getItemCount(): Int {
        return specificationList.size
    }

    class SpecificationViewHolder(private val binding: ItemSpecificationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(specification: ProductSpecification) {
            binding.tvSpecificationTitle.text = specification.title

            binding.tvSpecificationValue.text = specification.specification
        }

    }

}