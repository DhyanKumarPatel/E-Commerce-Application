import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code4galaxy.e_commerceapp.view.fragments.CartItemsCheckoutFragment
import com.code4galaxy.e_commerceapp.view.fragments.DeliveryFragment
import com.code4galaxy.e_commerceapp.view.fragments.PaymentFragment
import com.code4galaxy.e_commerceapp.view.fragments.SummaryFragment

class CheckoutPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> CartItemsCheckoutFragment()

            1 -> DeliveryFragment()

            2 -> PaymentFragment()

            3 -> SummaryFragment()

            else -> CartItemsCheckoutFragment()
        }
    }
}