package com.example.chat.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.data.helper.FirebaseHelper
import com.example.chat.data.Resource

class SignUpViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {
    private var mutableSinUpStatus: MutableLiveData<Resource<String?>> = MutableLiveData()
    val signUpStatus: LiveData<Resource<String?>>
        get() = mutableSinUpStatus

    fun signUp(email: String, password: String, username: String, byteArray: ByteArray) {
        mutableSinUpStatus.value = Resource.loading()
        firebaseHelper.signUp(email, password,
            {
                addUserToDb(email, username, byteArray)
            },
            {
                mutableSinUpStatus.value = Resource.error(it)
            })
    }

    private fun addUserToDb(email: String, username: String, byteArray: ByteArray) {
        firebaseHelper.addUserToDb(email, username, byteArray,
            {
                mutableSinUpStatus.value = Resource.success(null)
            },
            {
                mutableSinUpStatus.value = Resource.error(it)
            })
    }

}