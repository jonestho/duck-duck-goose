package com.game.duckduckgoose

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class ShopViewModel: ViewModel() {
    val _updateShop = MutableLiveData<Boolean>(false)
    val updateShop get(): LiveData<Boolean> = _updateShop

    var shopItems: ArrayList<Item>? = ArrayList()
    var currencyAmount: Int = 0
    fun loadData(items: ArrayList<Item>, currency: Int){
        shopItems = items
        currencyAmount = currency
    }

    fun purchaseHandler(recyclerView: RecyclerView): Int {
        val shopAdapter = recyclerView.adapter as? ShopAdapter

        return if (shopAdapter?.selectedPos != RecyclerView.NO_POSITION) {
            val selectedItem = shopAdapter?.shopItems?.get(shopAdapter.selectedPos)
            if (selectedItem?.cost!! > currencyAmount) 1
            else {
                currencyAmount -= selectedItem.cost
                0
            }
        } else 2
    }
}