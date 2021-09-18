package com.example.chat.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.data.Resource
import com.example.chat.data.helper.FirebaseHelper

class SignInViewModel(private val firebaseHelper: FirebaseHelper):ViewModel() {
    private var mutableSignInStatus:MutableLiveData<Resource<String?>> = MutableLiveData()
    val signInStatus:LiveData<Resource<String?>>
    get() = mutableSignInStatus
    fun signIn(email:String,password:String){
        mutableSignInStatus.value = Resource.loading()
        firebaseHelper.signIn(email,password,
            {
                mutableSignInStatus.value = Resource.success(null)
            },
            {
                mutableSignInStatus.value = Resource.error(it)
            })
    }
}