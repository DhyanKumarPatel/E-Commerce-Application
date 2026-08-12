package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.model.Category
import com.code4galaxy.e_commerceapp.model.CategoryResponse
import com.code4galaxy.e_commerceapp.repository.ICategoryRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: ICategoryRepository
) : ViewModel() {

    private val _categoryState =
        MutableLiveData<UIState<CategoryResponse>>()

    val categoryState: LiveData<UIState<CategoryResponse>>
        get() = _categoryState

    fun getCategories() {

        viewModelScope.launch {

            _categoryState.value = UIState.Loading

            try {

                val response = repository.getCategories()

                if (response.status == 0) {

                    _categoryState.value =
                        UIState.Success(response)

                } else {

                    _categoryState.value =
                        UIState.Error(response.message)
                }

            } catch (e: Exception) {

                _categoryState.value =
                    UIState.Error(
                        e.message ?: "Something went wrong"
                    )
            }
        }
    }
}