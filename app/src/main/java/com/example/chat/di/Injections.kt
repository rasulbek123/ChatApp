package com.example.chat.di

import com.example.chat.data.Settings
import com.example.chat.data.helper.FirebaseHelper
import com.example.chat.ui.auth.signin.SignInViewModel
import com.example.chat.ui.auth.signup.SignUpViewModel
import com.example.chat.ui.searchUser.SearchUserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
    single { FirebaseHelper(get(), get(), get()) }
    single { Settings(androidContext()) }
}
val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { SearchUserViewModel(get()) }
}