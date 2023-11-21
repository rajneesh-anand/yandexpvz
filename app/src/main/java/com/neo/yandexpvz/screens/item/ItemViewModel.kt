package com.neo.yandexpvz.screens.item

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.yandexpvz.model.GiftCard
import com.neo.yandexpvz.model.ItemCard
import com.neo.yandexpvz.repository.GiftRepository
import com.neo.yandexpvz.repository.ItemRepository
import com.neo.yandexpvz.utils.NetworkResult
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository,

    ): ViewModel() {

    var isLoading: Boolean by mutableStateOf(false)
    var errorText: String by mutableStateOf("")
    private var _itemsList: MutableLiveData<List<ItemCard>> = MutableLiveData(listOf())
    val itemList: LiveData<List<ItemCard>> = _itemsList


    fun initialize(){
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val response = itemRepository.fetchItems()) {
                    is NetworkResult.Success -> {
                        _itemsList.postValue(response.data!!.items)
                        isLoading = false
                    }
                    is NetworkResult.Error -> {
                        isLoading = false
                        errorText = response.message.toString()
                    }
                    else -> {
                        isLoading = false
                    }
                }

            } catch (exception: Exception) {
                isLoading = false
                Log.d("Network", "loginUser: ${exception.message.toString()}")
            }

        }
    }
}