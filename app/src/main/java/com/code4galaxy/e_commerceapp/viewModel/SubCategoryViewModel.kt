package com.code4galaxy.e_commerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.e_commerceapp.model.SubCategoryResponse
import com.code4galaxy.e_commerceapp.repository.ISubCategoryRepository
import com.code4galaxy.e_commerceapp.utils.UIState
import kotlinx.coroutines.launch

class SubCategoryViewModel(private val repository: ISubCategoryRepository): ViewModel() {

    private val _subCategoryState = MutableLiveData<UIState<SubCategoryResponse>>()
    val SubCategoryState: LiveData<UIState<SubCategoryResponse>>
        get() = _subCategoryState

    fun subCategory(categoryId: String){
        viewModelScope.launch {
            _subCategoryState.value = UIState.Loading

            try {
                val response = repository.getSubCategories(categoryId)

                if (response.status == 0){
                    _subCategoryState.value = UIState.Success(response)
                } else{
                    _subCategoryState.value =
                        UIState.Error(response.message)
                }
            } catch (e: Exception){
                _subCategoryState.value = UIState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}