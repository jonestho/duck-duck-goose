package com.game.duckduckgoose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewAccountViewModel: ViewModel() {
    private val db = Firebase.firestore
    val auth = Firebase.auth

    private val _account: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _isCreated = MutableLiveData<Boolean>()

    val account: LiveData<Boolean> get() = _account
    val isCreated: LiveData<Boolean> get() = _isCreated
    var isInitialized: Boolean = false

    // default firebase vals

    val tempFarmers = hashMapOf("farmers" to 0)
    val tempHonks = hashMapOf("honks" to 0)
    val tempIncrement = hashMapOf("inc" to 1)

    fun doSignUp(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    isInitialized = true
                    _account.value = true
                }.addOnFailureListener {
                    isInitialized = true
                    _account.value = false
                }
        }

    }

    private fun createNewUser(tempName: HashMap<String, String>){
        db.collection("accounts").document(auth.uid!!).set({val test = "test"})
        db.collection("accounts/${auth.uid!!}/stats")
            .document("farmers").set(tempFarmers)
        db.collection("accounts/${auth.uid!!}/stats")
            .document("totalHonks").set(tempHonks)
        db.collection("accounts/${auth.uid!!}/stats")
            .document("honkIncrement").set(tempIncrement)
        db.collection("accounts/${auth.uid!!}/username")
            .document("name").set(tempName)
    }

    fun createNewUserDoc(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tempName = hashMapOf("name" to username)
                createNewUser(tempName)

                isInitialized = true
                _isCreated.postValue(true)

            } catch (e: Exception) {
                println("Error: ${e.message}")
                _isCreated.postValue(false)
            }
        }

        isInitialized = true
    }
}