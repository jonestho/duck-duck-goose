package com.game.duckduckgoose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val auth = Firebase.auth
    private val _userID: MutableLiveData<String?> = MutableLiveData("")

    var isInitialized: Boolean = false

    val userID: LiveData<String?> get() = _userID

    init{
        _userID.postValue(auth.uid)
    }

    fun doLogin(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    isInitialized = true
                    _userID.postValue(it.user?.uid)
                }.addOnFailureListener {
                    isInitialized = true
                    _userID.value = null
                }
        }
    }
}