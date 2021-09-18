package com.example.chat.ui.auth.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.chat.R
import com.example.chat.data.ResourceState
import com.example.chat.databinding.FragmentSignInBinding
import org.koin.android.ext.android.inject

class SignInFragment:Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel:SignInViewModel by inject()
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentSignInBinding.bind(view)
        binding.tvGoToRegister.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            navController.navigate(action)
        }
        binding.btnLogin.setOnClickListener {
            var success = true
            binding.apply {
                if(userEmailorUserName.text.isNullOrEmpty()){
                    userEmailorUserName.error = "Fill the field"
                    success = false
                }
                if(signInPassword.text.isNullOrEmpty()){
                    signInPassword.error = "Fill the field"
                    success = false
                }
                if(!success) return@setOnClickListener
                else{
                    viewModel.signIn(userEmailorUserName.text.toString(),signInPassword.text.toString())
                }
            }
        }
        setObservers()
    }
    private fun setObservers(){
        viewModel.signInStatus.observe(viewLifecycleOwner,{
            when(it.status){
                ResourceState.LOADING->{
                    isloading(true)
                }
                ResourceState.SUCCESS->{
                    val action = SignInFragmentDirections.actionSignInFragmentToMainFragment()
                    navController.navigate(action)
                }
                ResourceState.ERROR->{
                    isloading(false)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun isloading(Loading:Boolean){
        binding.apply {
            loading.isVisible = Loading
            userEmailorUserName.isEnabled = !Loading
            signInPassword.isEnabled = !Loading
            btnLogin.isEnabled = !Loading
            tvGoToRegister.isEnabled = !Loading
        }
    }
}
