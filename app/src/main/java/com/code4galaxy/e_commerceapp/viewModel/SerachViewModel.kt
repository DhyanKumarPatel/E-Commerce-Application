package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.model.ProductResponse
import com.code4galaxy.e_commerceapp.repository.ISearchRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: ISearchRepository
) : ViewModel() {

    private val _searchState =
        MutableLiveData<UIState<ProductResponse>>()

    val searchState: LiveData<UIState<ProductResponse>>
        get() = _searchState


    fun searchProducts(searchText: String) {

        viewModelScope.launch {

            _searchState.value = UIState.Loading

            try {

                val response =
                    repository.searchProducts(searchText)

                if (response.status == 0) {

                    _searchState.value =
                        UIState.Success(response)

                } else {

                    _searchState.value =
                        UIState.Error(response.message)
                }

            } catch (e: Exception) {

                _searchState.value =
                    UIState.Error(
                        e.message ?: "Unable to search products"
                    )
            }
        }
    }
}