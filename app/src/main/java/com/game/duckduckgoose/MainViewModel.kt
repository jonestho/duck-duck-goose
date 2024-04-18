package com.game.duckduckgoose

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val name: String = "",
    val description: String = "",
    val cost: Int = 0,
    var bought: Boolean = false
): Parcelable

class MainViewModel: ViewModel() {
    // mutable data
    private val _gooseName = MutableLiveData<String>("")
    private val _totalClicks = MutableLiveData<Int>(0)
    private val _clickIncrement = MutableLiveData<Int>(1)
    private val _idleClickers = MutableLiveData<Int>(0)

    // accessible mutable data
    val gooseName: LiveData<String> get() = _gooseName
    val totalClicks: LiveData<Int> get() = _totalClicks
    val clickIncrement: LiveData<Int> get() = _clickIncrement
    val idleClickers: LiveData<Int> get() = _idleClickers

    // list of items bought
    var itemsBought: ArrayList<Item> = ArrayList<Item>()

    // firebase vars
    private val db = Firebase.firestore
    private var uid: String = ""

    // firebase logic stuff
    fun setUID(uid: String){
        this.uid = uid
    }

    fun loadUsername(){
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("accounts/${uid}/username")
                .document("name")
                .get()
                .addOnSuccessListener { document ->
                    _gooseName.value = document.data!!["name"].toString()
                }
        }
        print("USERNAME: $gooseName\n")
    }

    // game logic
    fun addClicks() {
        _totalClicks.value = _clickIncrement.value?.let { _totalClicks.value?.plus(it) }
    }
}