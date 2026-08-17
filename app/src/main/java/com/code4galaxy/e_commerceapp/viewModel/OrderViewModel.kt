package com.code4galaxy.e_commerceapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.model.OrderDetailsResponse
import com.code4galaxy.e_commerceapp.model.OrderRequest
import com.code4galaxy.e_commerceapp.model.OrderResponse
import com.code4galaxy.e_commerceapp.repository.IOrderRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: IOrderRepository
) : ViewModel() {

    private val _orderState =
        MutableLiveData<UIState<OrderResponse>>()

    val orderState: LiveData<UIState<OrderResponse>>
        get() = _orderState

    private val _orderDetailsState =
        MutableLiveData<UIState<OrderDetailsResponse>>()

    val orderDetailsState: LiveData<UIState<OrderDetailsResponse>>
        get() = _orderDetailsState


    fun placeOrder(orderRequest: OrderRequest) {

        viewModelScope.launch {

            _orderState.value = UIState.Loading

            try {

                val response =
                    repository.placeOrder(orderRequest)

                Log.d(
                    "ORDER_TEST",
                    "Response status=${response.status}, message=${response.message}, orderId=${response.order_id}"
                )

                if (response.status == 0) {

                    _orderState.value =
                        UIState.Success(response)

                } else {

                    _orderState.value =
                        UIState.Error(response.message)
                }

            } catch (e: Exception) {

                _orderState.value =
                    UIState.Error(
                        e.message ?: "Unable to place order"
                    )
            }
        }
    }


    fun getOrderDetails(orderId: Int) {

        viewModelScope.launch {

            _orderDetailsState.value =
                UIState.Loading

            try {

                val response =
                    repository.getOrderDetails(orderId)

                if (response.status == 0) {

                    _orderDetailsState.value =
                        UIState.Success(response)

                } else {

                    _orderDetailsState.value =
                        UIState.Error(response.message)
                }

            } catch (e: Exception) {

                _orderDetailsState.value =
                    UIState.Error(
                        e.message ?: "Unable to get order details"
                    )
            }
        }
    }
}