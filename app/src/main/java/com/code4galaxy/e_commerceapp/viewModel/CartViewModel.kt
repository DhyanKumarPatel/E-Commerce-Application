package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.repository.ICartRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: ICartRepository
) : ViewModel() {

    private val _cartState = MutableLiveData<UIState<List<CartEntity>>>()

    val cartState: LiveData<UIState<List<CartEntity>>>
        get() = _cartState

    private val _cartItemState =
        MutableLiveData<UIState<CartEntity?>>()

    val cartItemState: LiveData<UIState<CartEntity?>>
        get() = _cartItemState



    fun getCartItems(userId: String) {

        viewModelScope.launch {

            _cartState.value = UIState.Loading

            try {

                val items = repository.getCartItems(userId)

                _cartState.value = UIState.Success(items)

            } catch (e: Exception) {

                _cartState.value = UIState.Error( e.message ?: "Unable to load cart" )
            }
        }
    }

    fun getCartItem(userId: String, productId: String){
        viewModelScope.launch {
            try {

                val item = repository.getCartItem(userId,productId)

                _cartItemState.value = UIState.Success(item)

            }catch (e: Exception){
                _cartItemState.value = UIState.Error(e.message ?: "Unable to laod Cart Item")
            }
        }
    }


    fun addToCart(cartItem: CartEntity) {

        viewModelScope.launch {

            try {

                repository.insertCart(cartItem)

                val items = repository.getCartItems(cartItem.userId)

                _cartState.value = UIState.Success(items)

            } catch (e: Exception) {

                _cartState.value =
                    UIState.Error( e.message ?: "Unable to add product to cart" )
            }
        }
    }


    fun increaseQuantity(userId: String, productId: String,currentQuantity: Int) {

        viewModelScope.launch {

            try {

                val newQuantity = currentQuantity + 1

                repository.updateQuantity( userId, productId, newQuantity)

                val items =  repository.getCartItems(userId)

                _cartState.value = UIState.Success(items)

            } catch (e: Exception) {

                _cartState.value = UIState.Error(  e.message ?: "Unable to update quantity" )
            }
        }
    }


    fun decreaseQuantity( userId: String, productId: String, currentQuantity: Int ) {

        viewModelScope.launch {

            try {

                val newQuantity = currentQuantity - 1

                if (newQuantity <= 0) {

                    repository.deleteItem( userId, productId )

                } else {

                    repository.updateQuantity(userId, productId, newQuantity )
                }

                val items = repository.getCartItems(userId)

                _cartState.value =UIState.Success(items)

            } catch (e: Exception) {

                _cartState.value =   UIState.Error( e.message ?: "Unable to update cart" )
            }
        }
    }


    fun clearCart(userId: String) {

        viewModelScope.launch {

            try {

                repository.clearCart(userId)

                _cartState.value = UIState.Success(emptyList())

            } catch (e: Exception) {

                _cartState.value = UIState.Error(e.message ?: "Unable to clear cart")
            }
        }
    }
}