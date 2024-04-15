package com.game.duckduckgoose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShopViewModel: ViewModel() {
    private val _updateShop = MutableLiveData<Boolean>(false)
    val updateShop get(): LiveData<Boolean> = _updateShop

    var shopItems: ArrayList<Item>? = ArrayList()
    var currencyAmount: Int = 0
    fun loadData(items: ArrayList<Item>, currency: Int){
        shopItems = items
        currencyAmount = currency
    }

    fun purchaseHandler(){
        // TODO: Implement logic for buying item. Hint, use ViewHolder if possible.
    }
}