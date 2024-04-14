package com.game.duckduckgoose

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val name: String = "",
    val description: String = "",
    val cost: Int = 0,
    var bought: Boolean = false
): Parcelable

class MainViewModel {

}