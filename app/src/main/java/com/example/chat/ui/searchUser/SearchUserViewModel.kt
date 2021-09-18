package com.example.chat.ui.searchUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.data.Resource
import com.example.chat.data.helper.FirebaseHelper
import com.example.chat.data.model.User

class SearchUserViewModel(private val firebaseHelper: FirebaseHelper) : ViewModel() {
    private var mutableUserImage: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val statusImageUser: LiveData<Resource<List<User>>>
        get() = mutableUserImage

    fun getAllUsers() {
        mutableUserImage.value = Resource.loading()
        firebaseHelper.getAllUsers(
            {
                mutableUserImage.value = Resource.success(it)
            },
            {
                mutableUserImage.value = Resource.error(it)
            }
        )
    }
}